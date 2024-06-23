package com.petpal.petpalservice.service;


import org.springframework.stereotype.Service;

import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetMapper;
import com.petpal.petpalservice.model.dto.DocumentResponseDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDTO;
import com.petpal.petpalservice.model.dto.NewPetRequestDTO;
import com.petpal.petpalservice.model.dto.NewReminderRequestDTO;
import com.petpal.petpalservice.model.dto.PetResponseDTO;
import com.petpal.petpalservice.model.dto.PetUpdateRequestDTO;
import com.petpal.petpalservice.model.dto.ReminderResponseDTO;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDTO;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.DocumentRepository;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.ReminderRepository;
import com.petpal.petpalservice.model.entity.Reminder;
import com.petpal.petpalservice.model.entity.User;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;



import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final ReminderRepository reminderRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final DocumentRepository documentRepository;
    
    private final PetOwnerService petOwnerService;
    private final EmailSenderService emailService;
    private final PetMapper mapper;
    private final UserService userService;
    

    @Transactional
    public PetResponseDTO createPet(NewPetRequestDTO dto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        boolean exists = petRepository.existsByPetOwnerAliasAndName(owner.getAlias(),dto.getName());
        if(exists){
            throw new ResourceNotFoundException("Ya tienes una mascota con el nombre "+dto.getName());
        }
        Pet pet = mapper.convertToEntityPet(dto);
        pet.setOwner(owner);
        petRepository.save(pet);
        return mapper.convertToDtoPet(pet);
    }

    @Transactional(readOnly =true)
    public List<PetResponseDTO> getPetsByOwner(String alias) {
        User user = userService.getUserByAuth();
        PetOwner owner = petOwnerRepository.findByAlias(alias)
                    .orElseThrow(()-> new ResourceNotFoundException("El usuario con el alias "+alias+" no existe"));
        if(!user.getAlias().equals(alias) && !owner.isPetVisible() ){
            throw new ResourceNotFoundException("No puedes ver las mascotas de "+alias);
        }
        List<Pet> pets = petRepository.findByPetOwnerAlias(alias);
        return mapper.convertToListDtoPet(pets);
    }

    @Transactional(readOnly = true)
    public PetResponseDTO getPetByName(String alias, String name) {
        User user = userService.getUserByAuth();
        PetOwner owner = petOwnerRepository.findByAlias(alias)
                    .orElseThrow(()-> new ResourceNotFoundException("El usuario con el alias "+alias+" no existe"));
        if(!user.getAlias().equals(alias) && !owner.isPetVisible() ){
            throw new ResourceNotFoundException("No puedes ver las mascotas de "+alias);
        }
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(alias,name)
                 .orElseThrow(()-> new ResourceNotFoundException("La mascota con el nombre "+name+" no pertenece a "+alias)); 
        return mapper.convertToDtoPet(pet);
    }

    @Transactional
    public void deletePet(String name) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),name)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el nombre "+name+" no puede ser eliminada por "+owner.getAlias())); 
        petRepository.delete(pet);
    }

    @Transactional
    public PetResponseDTO updatePet(String name,PetUpdateRequestDTO dto){
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),name)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el nombre "+name+" no puede ser editada por "+owner.getAlias()));  

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
        return  mapper.convertToDtoPet(pet);
    }

    @Transactional
    public PetResponseDTO updateProfilePicture(String name,String path) {
        PetOwner petOwner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(petOwner.getAlias(),name)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el nombre "+name+" no puede ser editada por "+petOwner.getAlias()));
        pet.setImage(path);
        petRepository.save(pet);
        return mapper.convertToDtoPet(pet);
    }
   
    //Reminders
    @Transactional
    public ReminderResponseDTO createReminder(NewReminderRequestDTO dto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),dto.getPetName())
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el nombre "+ dto.getPetName()+ " no puede tener recordatorios por "+owner.getAlias())); 

        Reminder reminder = mapper.convertToEntityReminder(dto);
        reminder.setTime(LocalTime.parse(dto.getTime()));
        //asignamos el nextReminderDate segunla fecha actual y el dia de semana del recordatorio mas cercano
        LocalDate nextReminderDate = LocalDate.now();
        while(!reminder.getDays().contains(nextReminderDate.getDayOfWeek())){
            nextReminderDate = nextReminderDate.plusDays(1);
        }
        reminder.setNextDate(nextReminderDate);
        reminder.setPet(pet);   
        reminderRepository.save(reminder);
        return mapper.convertToDtoReminder(reminder);
    }

    @Transactional(readOnly = true)
    public List<ReminderResponseDTO> getRemindersByPetName(String petName) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes ver los recordatorios de la mascota con el nombre "+petName));
        List<Reminder> reminders = reminderRepository.findByPetId(pet.getId());
        return mapper.convertToListDtoReminder(reminders);
    }

    @Transactional(readOnly = true)
    public ReminderResponseDTO getReminderByPetName(String petName, Long idReminder) {
        Reminder reminder = reminderRepository.findByPetNameAndReminderId(petName, idReminder)
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el nombre "+petName));
        return mapper.convertToDtoReminder(reminder);
    }

    @Transactional
    public void deleteReminder(String petName, Long idReminder) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes eliminar los recordatorios de la mascota con el  nombre "+petName));
        Reminder reminder = reminderRepository.findByPetIdAndReminderId(pet.getId(), idReminder)
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+idReminder+" no pertenece a la mascota con el nombre "+petName));
        reminderRepository.delete(reminder);
    }

    @Transactional
    public ReminderResponseDTO updateReminder(String petName, ReminderUpdateRequestDTO dto) {
        Reminder reminder = reminderRepository.findByPetNameAndReminderId(petName, dto.getId())
                    .orElseThrow(()-> new ResourceNotFoundException("El recordatorio con el id "+dto.getId()+" no pertenece a la mascota con el nombre "+petName));
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
            reminder.setTime(LocalTime.parse(dto.getReminderTime()));
        }
        reminderRepository.save(reminder);
        return mapper.convertToDtoReminder(reminder);
    }

    @Transactional
    public void sendReminder(int idPet,String date,String time) {
        Pet pet = petRepository.findById(idPet)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+idPet+" no existe"));
        PetOwner owner = pet.getOwner();
        List<Reminder> reminders = reminderRepository.findByPetId(pet.getId());
        LocalDate currentDate = LocalDate.parse(date);
        LocalTime currentTime = LocalTime.parse(time);
        
        for(Reminder reminder: reminders){
            
            if(reminder.getNextDate().equals(currentDate) &&  reminder.getTime().equals(currentTime)){
                String to = owner.getEmail();
                String subject = "Recordatorio de "+reminder.getName();
                Map<String, Object> variables = new HashMap<>();
                variables.put("petName", pet.getName());
                variables.put("reminderName", reminder.getName());
                variables.put("reminderDescription", reminder.getDescription()); 
                emailService.sendEmail(to, subject, "reminderPet.html", variables);
                LocalDate nextReminderDate = currentDate;
                do{
                    nextReminderDate = nextReminderDate.plusDays(1);
                }while(!reminder.getDays().contains(nextReminderDate.getDayOfWeek()));
                reminder.setNextDate(nextReminderDate);
                reminderRepository.save(reminder);
            }
        }
    }

    //Medical Records
    @Transactional
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO dto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),dto.getPetName())
                    .orElseThrow(()-> new ResourceNotFoundException(owner.getAlias()+" no puede agregar registros medicos a la mascota con el nombre "+dto.getPetName()));
        MedicalRecord medicalRecord = mapper.convertToEntityMedicalRecord(dto);
        medicalRecord.setDate(LocalDate.parse(dto.getDate()));
        medicalRecord.setPet(pet);
        medicalRecordRepository.save(medicalRecord);
        return mapper.convertToDtoMedicalRecord(medicalRecord);
    }
    
    @Transactional(readOnly = true)
    public List<MedicalRecordResponseDTO> getMedicalRecordsByPetName(String petName) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes ver los registros medicos de la mascota con el nombre "+petName));
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPetId(pet.getId());
        return mapper.convertToListDtoMedicalRecord(medicalRecords);
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponseDTO getMedicalRecordByPetName(String petName, Long idRecord) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes ver los registros medicos de la mascota con el nombre "+petName));
        MedicalRecord medicalRecord =  medicalRecordRepository.findByPetIdAndRecordId(pet.getId(), idRecord)
                    .orElseThrow(()-> new ResourceNotFoundException(petName+" no tiene un registro medico con el id "+idRecord));
        return mapper.convertToDtoMedicalRecord(medicalRecord);
    }

    @Transactional
    public void deleteMedicalRecord(String petName, Long idRecord) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes eliminar los registros medicos de la mascota con el nombre "+petName));
        MedicalRecord medicalRecord =  medicalRecordRepository.findByPetIdAndRecordId(pet.getId(), idRecord)
                    .orElseThrow(()-> new ResourceNotFoundException(petName+" no tiene un registro medico con el id "+idRecord));
        medicalRecordRepository.delete(medicalRecord);
    }

    @Transactional
    public void addDocumentToMedicalRecord(String petName, Long idRecord, String path) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes agregar documentos a los registros medicos de la mascota con el nombre "+petName));
        MedicalRecord medicalRecord =  medicalRecordRepository.findByPetIdAndRecordId(pet.getId(), idRecord)
                    .orElseThrow(()-> new ResourceNotFoundException(petName+" no tiene un registro medico con el id "+idRecord));
        Document document = new Document();
        document.setLink(path);
        document.setRecord(medicalRecord);
        documentRepository.save(document);
    }

    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> getDocumentsByMedicalRecord(String petName, Long idRecord) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(),petName)
                    .orElseThrow(()-> new ResourceNotFoundException("No puedes ver los documentos de los registros medicos de la mascota con el nombre "+petName));
        MedicalRecord medicalRecord =  medicalRecordRepository.findByPetIdAndRecordId(pet.getId(), idRecord)
                    .orElseThrow(()-> new ResourceNotFoundException(petName+" no tiene un registro medico con el id "+idRecord));
        List<Document> documents = documentRepository.findByRecord(medicalRecord.getId());
        return mapper.convertToListDtoDocument(documents);
    }
}