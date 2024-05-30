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
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;
import com.petpal.petpalservice.model.dto.ReminderResponseDto;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDto;
import com.petpal.petpalservice.model.entity.Reminder;

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
    private final PetMapper mapper;

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
        petRepository.save(pet);
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional(readOnly = true)
    public List<ReminderResponseDto> getRemindersByPetId(int idPet) {
        //Set<Reminder> reminders = petRepository.findByPetId(idPet)
                    //.orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no tiene recordatorios")); 
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe"));
        Set<Reminder> reminders = pet.getReminders();
        return mapper.entityToListDtoReminder(reminders);
    }

    @Transactional(readOnly = true)
    public ReminderResponseDto getReminderByPetId(int idPet, int idReminder) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe"));
        Reminder reminder = pet.getReminders().stream()
                    .filter(r -> r.getId() == idReminder)
                    .findFirst()
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el id "+idPet));
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional
    public ReminderResponseDto updateReminder(int idPet, int id, ReminderUpdateRequestDto dto) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe")); 
        Reminder reminder = pet.getReminders().stream()
                    .filter(r -> r.getId() == id)
                    .findFirst()
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+id+" no pertenece a la mascota con el id "+idPet));
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
        petRepository.save(pet);
        return mapper.entityToDtoReminder(reminder);
    }

    @Transactional
    public void deleteReminder(int idPet, int idReminder) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no tiene recordatorios")); 
        Reminder reminder = pet.getReminders().stream()
                    .filter(r -> r.getId() == idReminder)
                    .findFirst()
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el id "+idPet));
        pet.getReminders().remove(reminder);
        petRepository.save(pet);
    }

}
