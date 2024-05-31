package com.petpal.petpalservice.service;


import org.springframework.stereotype.Service;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetMapper;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.dto.petUpdateRequestDto;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.ReminderRepository;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;
import com.petpal.petpalservice.model.dto.ReminderResponseDto;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDto;
import com.petpal.petpalservice.model.entity.Reminder;
import com.petpal.petpalservice.service.EmailSenderService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;



import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetOwnerRepository PetOwnerRepository;
    private final ReminderRepository reminderRepository;
    private final PetMapper mapper;
    private final EmailSenderService emailService;

    @Transactional
    public PetResponseDto createPet(PetRequestDto dto) {
        PetOwner owner = PetOwnerRepository.findById(dto.getOwnerId());
        Pet pet = mapper.dtoToEntityPet(dto);
        pet.setPetOwner(owner);
        petRepository.save(pet);
        return mapper.entityToDtoPet(pet);
    }

    @Transactional(readOnly = true)
    public PetResponseDto getPetById(int ownerId, int id) {
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                 .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no le pertenece al dueño co el id "+ownerId)); 
        return mapper.entityToDtoPet(pet);
    }

    @Transactional
    public void deletePet(int ownerId, int id) {
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no puede ser eliminada por dueño con el id "+ownerId)); 
        petRepository.delete(pet);
    }

    @Transactional
    public PetResponseDto updatePet(int ownerId, int id, petUpdateRequestDto dto){
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no puede ser editada por el dueño con el id "+ownerId));  

        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if(value != null){
                    Field fieldPet = pet.getClass().getDeclaredField(field.getName());
                    fieldPet.setAccessible(true);
                    ReflectionUtils.setField(fieldPet,pet,value);
                    fieldPet.setAccessible(false);
                }
            field.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pet = petRepository.save(pet);
        return  mapper.entityToDtoPet(pet);
    }


    @Transactional(readOnly =true)
    public List<PetResponseDto> getPetsByOwnerId(int ownerId) {
        List<Pet> pets = petRepository.findByPetOwnerId(ownerId)
                    .orElseThrow(()-> new ResourceNotFoundException("El dueño "+ownerId+" no tiene mascotas")); 
        return mapper.entityToListDtoPet(pets);
    }

    @Transactional
    public ReminderResponseDto createReminder(ReminderRequestDto dto) {
        Pet pet = petRepository.findById(dto.getIdPet())
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+dto.getIdPet()+" no existe")); 
        Reminder reminder = mapper.dtoToEntityReminder(dto);
        reminder.setReminderTime(LocalTime.parse(dto.getReminderTime()));
        //asignamos el nextReminderDate segunla fecha actual y el dia de semana del recordatorio mas cercano
        LocalDate nextReminderDate = LocalDate.now();
        while(!reminder.getDays().contains(nextReminderDate.getDayOfWeek())){
            nextReminderDate = nextReminderDate.plusDays(1);
        }
        reminder.setNextReminderDate(nextReminderDate);
        reminder.setPet(pet);   
        pet.getReminders().add(reminder);

        reminderRepository.save(reminder);
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional(readOnly = true)
    public List<ReminderResponseDto> getRemindersByPetId(int idPet) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe"));
        Set<Reminder> reminders = pet.getReminders();
        return mapper.entityToListDtoReminder(reminders);
    }

    @Transactional(readOnly = true)
    public ReminderResponseDto getReminderByPetId(int idPet, int idReminder) {
        Reminder reminder = reminderRepository.findByPetIdAndReminderId(idPet, idReminder)
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el id "+idPet));
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional
    public ReminderResponseDto updateReminder(int idPet, int idReminder, ReminderUpdateRequestDto dto) {
        Reminder reminder = reminderRepository.findByPetIdAndReminderId(idPet, idReminder)
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el id "+idPet));
        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if(value != null){
                    Field fieldReminder = reminder.getClass().getDeclaredField(field.getName());
                    fieldReminder.setAccessible(true);
                    ReflectionUtils.setField(fieldReminder,reminder,value);
                    fieldReminder.setAccessible(false);
                }
            field.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dto.getReminderTime() != null){
            reminder.setReminderTime(LocalTime.parse(dto.getReminderTime()));
        }
        reminderRepository.save(reminder);
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional
    public void deleteReminder(int idPet, int idReminder) {
        Reminder reminder = reminderRepository.findByPetIdAndReminderId(idPet, idReminder)
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el id "+idPet));
        reminderRepository.delete(reminder);
    }

    @Transactional
    public void sendReminder(int idPet,String date,String time) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe"));
        PetOwner owner = pet.getPetOwner();
        Set<Reminder> reminders = pet.getReminders();
        LocalDate currentDate = LocalDate.parse(date);
        LocalTime currentTime = LocalTime.parse(time);
        
        for(Reminder reminder: reminders){
            
            if(reminder.getNextReminderDate().equals(currentDate) &&  LocalTime.parse(reminder.getReminderTime()).equals(currentTime)){
                String to = owner.getOwnerEmail();
                String subject = "Recordatorio de "+reminder.getReminderName();
                String text = "Recordatorio de "+reminder.getReminderName()+" para tu mascota "+pet.getPetName()+" \n ";
                emailService.sendEmail(to, subject, text);
                LocalDate nextReminderDate = currentDate;
                do{
                    nextReminderDate = nextReminderDate.plusDays(1);
                }while(!reminder.getDays().contains(nextReminderDate.getDayOfWeek()));
                reminder.setNextReminderDate(nextReminderDate);
                reminderRepository.save(reminder);
            }
        }
    }

}
