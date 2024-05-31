package com.petpal.petpalservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetMapper;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;
import com.petpal.petpalservice.model.dto.ReminderResponseDto;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDto;
import com.petpal.petpalservice.model.dto.petUpdateRequestDto;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Reminder;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.ReminderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private PetMapper petMapper;

    @InjectMocks
    private PetService petService;

    @Mock
    private EmailSenderService emailService;

    @Test
    void testCreatePet() {
        //Arrange
        PetRequestDto petRequestDto = new PetRequestDto();
        petRequestDto.setOwnerId(1);
        petRequestDto.setPetName("Firulais");
        petRequestDto.setPetSpecies("Perro");
        petRequestDto.setPetBreed("Labrador");
        petRequestDto.setPetAge(5);
        petRequestDto.setPetSex("Macho");

        PetOwner owner = new PetOwner();
        owner.setId(1);
        
        Pet pet = new Pet();
        pet.setId(1);
        pet.setPetName(petRequestDto.getPetName());
        pet.setPetSpecies(petRequestDto.getPetSpecies());
        pet.setPetBreed(petRequestDto.getPetBreed());
        pet.setPetAge(petRequestDto.getPetAge());
        pet.setPetSex(petRequestDto.getPetSex());
        
        
         // Configuramos el mock de petRepository
        when(petOwnerRepository.findById(petRequestDto.getOwnerId())).thenReturn(owner);
        // Configuramos el mock de petMapper
        when(petMapper.dtoToEntityPet(petRequestDto)).thenReturn(pet);
        when(petMapper.entityToDtoPet(pet)).thenReturn(new PetResponseDto(pet.getId(), pet.getPetName(), pet.getPetSpecies(), pet.getPetBreed(), pet.getPetAge(), pet.getPetSex()));

        //Act
        PetResponseDto result = petService.createPet(petRequestDto);

        //Assert
        Assert.notNull(result, "El resultado no debe ser nulo");
        assertEquals(petRequestDto.getPetName(), result.getPetName());
        assertEquals(petRequestDto.getPetSpecies(), result.getPetSpecies());
        assertEquals(petRequestDto.getPetBreed(), result.getPetBreed());
        assertEquals(petRequestDto.getPetAge(), result.getPetAge());
        assertEquals(petRequestDto.getPetSex(), result.getPetSex());
        
        // Verificamos que el método dtoToEntityPet del mock petMapper se haya llamado una vez con el requestDTO
        verify(petMapper, times(1)).dtoToEntityPet(petRequestDto);
        // Verificamos que el método save del mock petRepository se haya llamado una vez con el pet
        verify(petRepository, times(1)).save(pet);
        // Verificamos que el método entityToDtoPet del mock petMapper se haya llamado una vez con el pet
        verify(petMapper, times(1)).entityToDtoPet(pet);


    }

    @Test
    void testCreateReminderSuccessfully() {
        //Arrange
        ReminderRequestDto reminderRequestDto = new ReminderRequestDto();
        reminderRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        reminderRequestDto.setIdPet(1);
        reminderRequestDto.setReminderDescription("Recordar darle de comer a Firulais");
        reminderRequestDto.setReminderName("Comida de Firulais");
        reminderRequestDto.setReminderTime("12:00:00");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setReminders(new HashSet<>());

        Reminder reminder = new Reminder();
        reminder.setId(1);
        reminder.setDays(reminderRequestDto.getDays());
        reminder.setReminderDescription(reminderRequestDto.getReminderDescription());
        reminder.setReminderName(reminderRequestDto.getReminderName());
        reminder.setReminderTime(LocalTime.parse(reminderRequestDto.getReminderTime()));        
        reminder.setNextReminderDate(LocalDate.now());
        reminder.setPet(pet);

        // Configuramos el mock de petRepository
        when(petRepository.findById(reminderRequestDto.getIdPet())).thenReturn(Optional.of(pet));
        // Configuramos el mock de petMapper
        when(petMapper.dtoToEntityReminder(reminderRequestDto)).thenReturn(reminder);
        pet.getReminders().add(reminder);

        when(reminderRepository.save(reminder)).thenReturn(reminder);
        when(petMapper.entityToDtoReminder(reminder)).thenReturn(new ReminderResponseDto(reminder.getId(),reminder.getReminderName(), reminder.getReminderDescription(),reminder.getNextReminderDate().toString(), reminder.getReminderTime().toString(), reminder.getDays().toString(), reminder.getPet().getId()));
        //Act

        ReminderResponseDto result = petService.createReminder(reminderRequestDto);

        //Assert
        Assert.notNull(result);
        assertEquals(reminderRequestDto.getReminderName(), result.getReminderName());
        assertEquals(reminderRequestDto.getReminderDescription(), result.getReminderDescription());
        assertEquals(reminderRequestDto.getReminderTime(), result.getReminderTime());
        assertEquals(reminderRequestDto.getDays().toString(), result.getDays());
        assertEquals(reminderRequestDto.getIdPet(), result.getIdPet());

        //Verify
        verify(petRepository, times(1)).findById(reminderRequestDto.getIdPet());
        verify(petMapper, times(1)).dtoToEntityReminder(reminderRequestDto);
        verify(petMapper, times(1)).entityToDtoReminder(reminder);

    }

    @Test
    void testCreateReminder_NotFoundPetId() {
        //Arrange
        ReminderRequestDto reminderRequestDto = new ReminderRequestDto();
        reminderRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        reminderRequestDto.setIdPet(1);
        reminderRequestDto.setReminderDescription("Recordar darle de comer a Firulais");
        reminderRequestDto.setReminderName("Comida de Firulais");
        reminderRequestDto.setReminderTime("12:00:00");

        // Configuramos el mock de petRepository
        when(petRepository.findById(reminderRequestDto.getIdPet())).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.createReminder(reminderRequestDto));

        //Verify
        verify(petMapper, never()).dtoToEntityReminder(reminderRequestDto);
        verify(petRepository, never()).save(any());
        verify(petMapper, never()).entityToDtoReminder(any());
    }
    
    @Test
    void testDeletePetSuccessfully() {
        //Arrange
        int ownerId = 1;
        int petId = 1;

        Pet pet = new Pet();
        pet.setId(petId);

        PetOwner owner = new PetOwner();
        owner.setId(ownerId);

         // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(ownerId, petId)).thenReturn(Optional.of(pet));

        //Act
        assertDoesNotThrow(()-> petService.deletePet(ownerId, petId));

        //Assert
        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    void testDeletePet_NotFoundPetIdInOwnerId() {
        //Arrange
        int ownerId = 1;
        int petId = 1;

        Pet pet = new Pet();
        pet.setId(petId);

        PetOwner owner = new PetOwner();
        owner.setId(ownerId);

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(ownerId, petId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.deletePet(ownerId, petId));

        //Assert
        verify(petRepository, never()).delete(pet);
    }

    @Test
    void testDeleteReminderSuccessfully() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        Reminder reminder = new Reminder();
        reminder.setId(reminderId);

        // Configuramos el mock de petRepository
        when(reminderRepository.findByPetIdAndReminderId(petId,reminderId)).thenReturn(Optional.of(reminder));

        //Act
        assertDoesNotThrow(()-> petService.deleteReminder(petId, reminderId));
        //Verify
        verify(reminderRepository, times(1)).delete(reminder);
    }

    @Test
    void testDeleteReminder_NotFoundReminderIdInPetId() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        // Configuramos el mock de petRepository
        when(reminderRepository.findByPetIdAndReminderId(petId,reminderId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.deleteReminder(petId, reminderId));

        //Verify
        verify(reminderRepository, never()).delete(any());
    }

    @Test
    void testGetPetByIdSuccessfully() {
        //Arrange
        Pet pet = new Pet();
        pet.setId(1);

        PetOwner owner = new PetOwner();
        owner.setId(1);

         // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(1, 1)).thenReturn(Optional.of(pet));
        // Configuramos el mock de petMapper
        PetResponseDto petResponseDto = new PetResponseDto();
        petResponseDto.setId(pet.getId());
        when(petMapper.entityToDtoPet(pet)).thenReturn(petResponseDto);

        //Act
        PetResponseDto result = petService.getPetById(1, 1);

        //Assert
        assertNotNull(result);
        assertEquals(pet.getId(), result.getId());
        
    }

    @Test
    void testGetPetById_NotFoundPetIdinOwnerId() {
        //Arrange
        Pet pet = new Pet();
        pet.setId(1);

        PetOwner owner = new PetOwner();
        owner.setId(1);

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(1, 1)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.getPetById(1, 1));

        //Assert
        verify(petMapper, never()).entityToDtoPet(pet);
    }

    @Test
    void testGetPetsByOwnerIdSuccessfully() {
        //Arrange
        PetOwner owner = new PetOwner();
        owner.setId(1);

        Pet pet1 = new Pet();
        pet1.setId(1);

        Pet pet2 = new Pet();
        pet2.setId(2);

        List<Pet> pets = Arrays.asList(pet1, pet2);

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerId(1)).thenReturn(Optional.of(pets));

        // Configuramos el mock de petMapper
        PetResponseDto petResponseDto1 = new PetResponseDto();
        petResponseDto1.setId(pet1.getId());
        PetResponseDto petResponseDto2 = new PetResponseDto();
        petResponseDto2.setId(pet2.getId());

        List<PetResponseDto> petResponseDtos = Arrays.asList(petResponseDto1, petResponseDto2);
        when(petMapper.entityToListDtoPet(pets)).thenReturn(petResponseDtos);

        //Act
        List<PetResponseDto> result = petService.getPetsByOwnerId(1);

        //Assert
        assertNotNull(result);
        assertEquals(petResponseDtos.size(), result.size());

        //Verify
        verify(petRepository, times(1)).findByPetOwnerId(1);
        verify(petMapper, times(1)).entityToListDtoPet(pets);
    }

    @Test
    void testGetPetsByOwnerId_NotFoundOwnerId() {
        //Arrange
        int ownerId = 99;

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerId(ownerId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.getPetsByOwnerId(ownerId));

        //Verify
        verify(petMapper, never()).entityToListDtoPet(any());
    }

    @Test
    void testGetReminderByPetIdSuccessfully() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setReminders(new HashSet<>());

        Reminder reminder = new Reminder();
        reminder.setId(reminderId);
        pet.getReminders().add(reminder);

        // Configuramos el mock de reminderRepository
        when(reminderRepository.findByPetIdAndReminderId(petId,reminderId)).thenReturn(Optional.of(reminder));
        // Configuramos el mock de reminderMapper
        ReminderResponseDto reminderResponseDto = new ReminderResponseDto();
        reminderResponseDto.setId(reminder.getId());
        when(petMapper.entityToDtoReminder(reminder)).thenReturn(reminderResponseDto);

        //Act
        ReminderResponseDto result = petService.getReminderByPetId(petId, reminderId);

        //Assert
        assertNotNull(result);
        assertEquals(reminder.getId(), result.getId());


    }

    @Test
    void testGetReminderByPetId_NotFoundReminderIdInPetId() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        // Configuramos el mock de reminderRepository
        when(reminderRepository.findByPetIdAndReminderId(petId,reminderId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.getReminderByPetId(petId, reminderId));

        //Verify
        verify(petMapper, never()).entityToDtoReminder(any());
    }
    
    @Test
    void testGetRemindersByPetIdSuccessfully() {
        //Arrange
        int petId = 1;

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setReminders(new HashSet<>());

        Reminder reminder1 = new Reminder();
        reminder1.setId(1);
        pet.getReminders().add(reminder1);
        Reminder reminder2 = new Reminder();
        reminder2.setId(2);
        pet.getReminders().add(reminder2);

        // Configuramos el mock de petRepository
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        // Configuramos el mock de petMapper
        ReminderResponseDto reminderResponseDto1 = new ReminderResponseDto();
        reminderResponseDto1.setId(reminder1.getId());
        ReminderResponseDto reminderResponseDto2 = new ReminderResponseDto();
        reminderResponseDto2.setId(reminder2.getId());
        List<ReminderResponseDto> reminderResponseDtos = Arrays.asList(reminderResponseDto1, reminderResponseDto2);
        when(petMapper.entityToListDtoReminder(pet.getReminders())).thenReturn(reminderResponseDtos);

        //Act
        List<ReminderResponseDto> result = petService.getRemindersByPetId(petId);

        //Assert
        assertNotNull(result);
        assertEquals(reminderResponseDtos.size(), result.size());

        //Verify
        verify(petRepository, times(1)).findById(petId);
        verify(petMapper, times(1)).entityToListDtoReminder(pet.getReminders());
        
    }

    @Test
    void testGetRemindersByPetId_NotFoundPetId() {
        //Arrange
        int petId = 1;

        // Configuramos el mock de petRepository
        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.getRemindersByPetId(petId));

        //Verify
        verify(petMapper, never()).entityToListDtoReminder(any());
    }

    @Test
    void testUpdatePetSuccessfully() {
        //Arrange
        int ownerId = 1;
        int petId = 1;

        petUpdateRequestDto petUpdateRequestDto = new petUpdateRequestDto();
        petUpdateRequestDto.setPetName("Firulais");
        petUpdateRequestDto.setPetSpecies("Perro");
        petUpdateRequestDto.setPetBreed("Labrador");
        petUpdateRequestDto.setPetAge(5);
        petUpdateRequestDto.setPetSex("Macho");

        Pet existingPet = new Pet();
        existingPet.setId(petId);
        existingPet.setPetName("Firulais");
        existingPet.setPetSpecies("Perro");
        existingPet.setPetBreed("Labrador");
        existingPet.setPetAge(5);
        existingPet.setPetSex("Macho");

        Pet updatedPet = new Pet();
        updatedPet.setId(petId);
        updatedPet.setPetName(petUpdateRequestDto.getPetName());
        updatedPet.setPetSpecies(petUpdateRequestDto.getPetSpecies());
        updatedPet.setPetBreed(petUpdateRequestDto.getPetBreed());
        updatedPet.setPetAge(petUpdateRequestDto.getPetAge());
        updatedPet.setPetSex(petUpdateRequestDto.getPetSex());

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(ownerId, petId)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(existingPet)).thenReturn(updatedPet);
        // Configuramos el mock de petMapper
        PetResponseDto petResponseDto = new PetResponseDto();
        petResponseDto.setId(updatedPet.getId());
        petResponseDto.setPetName(updatedPet.getPetName());
        petResponseDto.setPetSpecies(updatedPet.getPetSpecies());
        petResponseDto.setPetBreed(updatedPet.getPetBreed());
        petResponseDto.setPetAge(updatedPet.getPetAge());
        petResponseDto.setPetSex(updatedPet.getPetSex());
        when(petMapper.entityToDtoPet(updatedPet)).thenReturn(petResponseDto);

        //Act
        PetResponseDto result = petService.updatePet(ownerId, petId, petUpdateRequestDto);

        //Assert
        assertNotNull(result);
        assertEquals(updatedPet.getId(), result.getId());
        assertEquals(updatedPet.getPetName(), result.getPetName());
        assertEquals(updatedPet.getPetSpecies(), result.getPetSpecies());
        assertEquals(updatedPet.getPetBreed(), result.getPetBreed());
        assertEquals(updatedPet.getPetAge(), result.getPetAge());
        assertEquals(updatedPet.getPetSex(), result.getPetSex());

    }

    @Test
    void testUpdatePet_NotFoundPetIdInOwnerId() {
        //Arrange
        int ownerId = 1;
        int petId = 1;

        petUpdateRequestDto petUpdateRequestDto = new petUpdateRequestDto();
        petUpdateRequestDto.setPetName("Firulais");
        petUpdateRequestDto.setPetSpecies("Perro");
        petUpdateRequestDto.setPetBreed("Labrador");
        petUpdateRequestDto.setPetAge(5);
        petUpdateRequestDto.setPetSex("Macho");

        // Configuramos el mock de petRepository
        when(petRepository.findByPetOwnerIdAndPetId(ownerId, petId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.updatePet(ownerId, petId, petUpdateRequestDto));

        //Verify
        verify(petRepository, never()).save(any());
    }
   
    @Test
    void testUpdateReminderSuccessfully() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        ReminderUpdateRequestDto reminderUpdateRequestDto = new ReminderUpdateRequestDto();
        reminderUpdateRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        reminderUpdateRequestDto.setReminderDescription("Recordar darle de comer a Firulais");
        reminderUpdateRequestDto.setReminderName("Comida de Firulais");
        reminderUpdateRequestDto.setReminderTime("12:00:00");

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setReminders(new HashSet<>());
        
        Reminder existingReminder = new Reminder();
        existingReminder.setId(reminderId);
        existingReminder.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        existingReminder.setReminderDescription("Recordar darle de comer a Firulais");
        existingReminder.setReminderName("Comida de Firulais");
        existingReminder.setReminderTime(LocalTime.parse("12:00:00"));
        existingReminder.setNextReminderDate(LocalDate.now());
        existingReminder.setPet(pet);

        pet.getReminders().add(existingReminder);

        Reminder updatedReminder = new Reminder();
        updatedReminder.setId(reminderId);
        updatedReminder.setDays(reminderUpdateRequestDto.getDays());
        updatedReminder.setReminderDescription(reminderUpdateRequestDto.getReminderDescription());
        updatedReminder.setReminderName(reminderUpdateRequestDto.getReminderName());
        updatedReminder.setReminderTime(LocalTime.parse(reminderUpdateRequestDto.getReminderTime()));
        updatedReminder.setNextReminderDate(LocalDate.now());
        updatedReminder.setPet(pet);

        // Configuramos el mock de petRepository
        when(reminderRepository.findByPetIdAndReminderId(petId, reminderId)).thenReturn(Optional.of(existingReminder));
        when(reminderRepository.save(existingReminder)).thenReturn(updatedReminder);

        // Configuramos el mock de petMapper
        ReminderResponseDto reminderResponseDto = new ReminderResponseDto();
        reminderResponseDto.setId(updatedReminder.getId());
        reminderResponseDto.setReminderName(updatedReminder.getReminderName());
        reminderResponseDto.setReminderDescription(updatedReminder.getReminderDescription());
        reminderResponseDto.setReminderTime(updatedReminder.getReminderTime().toString());
        reminderResponseDto.setDays(updatedReminder.getDays().toString());
        when(petMapper.entityToDtoReminder(updatedReminder)).thenReturn(reminderResponseDto);

        //Act
        ReminderResponseDto result = petService.updateReminder(petId, reminderId, reminderUpdateRequestDto);

        //Assert
        assertNotNull(result);
        assertEquals(updatedReminder.getId(), result.getId());
        assertEquals(updatedReminder.getReminderName(), result.getReminderName());
        assertEquals(updatedReminder.getReminderDescription(), result.getReminderDescription());
        assertEquals(updatedReminder.getReminderTime().toString(), result.getReminderTime());
        assertEquals(updatedReminder.getDays().toString(), result.getDays());
    }

    @Test
    void testUpdateReminder_NotFoundReminderIdInPetId() {
        //Arrange
        int petId = 1;
        int reminderId = 1;

        ReminderUpdateRequestDto reminderUpdateRequestDto = new ReminderUpdateRequestDto();
        reminderUpdateRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        reminderUpdateRequestDto.setReminderDescription("Recordar darle de comer a Firulais");
        reminderUpdateRequestDto.setReminderName("Comida de Firulais");
        reminderUpdateRequestDto.setReminderTime("12:00:00");

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setReminders(new HashSet<>());

        // Configuramos el mock de petRepository
        when(reminderRepository.findByPetIdAndReminderId(petId, reminderId)).thenReturn(Optional.empty());

        //Act
        assertThrows(ResourceNotFoundException.class, ()-> petService.updateReminder(petId, reminderId, reminderUpdateRequestDto));

        //Verify
        verify(petRepository, never()).save(pet);
    }

    @Test
    void sendEmailReminderSuccessfully(){
        //Arrange
        int petId = 1;
        String date = "2024-05-30";
        String time = "12:00:00";


        PetOwner owner = new PetOwner();
        owner.setOwnerEmail("example@example.com");

        Pet pet = new Pet();
        pet.setPetName("Firulais");
        pet.setPetOwner(owner);
        pet.setReminders(new HashSet<>());

        Reminder reminder = new Reminder();
        reminder.setNextReminderDate(LocalDate.parse(date));
        reminder.setReminderTime(LocalTime.parse(time));
        reminder.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));

        pet.getReminders().add(reminder);

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);

        // Act
        assertDoesNotThrow(() -> petService.sendReminder(petId, date, time));

        // Assert
        verify(emailService, times(1)).sendEmail(eq("example@example.com"), anyString(), anyString());
    }

    @Test
    void sendEmailReminder_NotFoundPetId(){
        //Arrange
        int petId = 1;
        String date = "2024-05-30";
        String time = "12:00:00";

        // Configuramos el mock de petRepository
        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        // Act
        assertThrows(ResourceNotFoundException.class, () -> petService.sendReminder(petId, date, time));

        // Assert
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}
