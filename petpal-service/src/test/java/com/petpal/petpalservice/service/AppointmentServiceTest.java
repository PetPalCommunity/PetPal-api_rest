package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.AppointmentMapper;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private AppointmentService appointmentService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private VetRepository vetRepository;
    @Mock
    private AppointmentMapper appointmentMapper;

    @Test
    void createAppointment() {
        //Arrange
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setIdPet(0);
        dto.setIdVet(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setTime(Time.valueOf("01:00:00"));
        dto.setReason("hi");

        Pet pet = new Pet();
        pet.setIdPet(0);

        Vet vet = new Vet();
        vet.setId(0);

        Appointment app = new Appointment();
        app.setIdAppointment(0);
        app.setPet(pet);
        app.setVet(vet);
        app.setDate(dto.getDate());
        app.setTime(dto.getTime());
        app.setReason(dto.getReason());
        app.setConfirm(false);

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.of(pet));
        when(vetRepository.findById(dto.getIdVet())).thenReturn(Optional.of(vet));
        when(appointmentRepository.save(app)).thenReturn(app);

        Appointment result = appointmentService.createAppointment(dto);

        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getTime(), result.getTime());
        assertEquals(dto.getReason(), result.getReason());
        assertFalse(app.isConfirm());

    }

    @Test
    void createAppointmentPetNotFound() {
        //Arrange
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setIdPet(0);
        dto.setIdVet(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setTime(Time.valueOf("01:00:00"));
        dto.setReason("hi");

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class, () -> appointmentService.createAppointment(dto));
    }

    @Test
    void createAppointmentVetNotFound() {
        //Arrange
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setIdPet(0);
        dto.setIdVet(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setTime(Time.valueOf("01:00:00"));
        dto.setReason("hi");

        Pet pet = new Pet();
        pet.setIdPet(0);

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.of(pet));
        when(vetRepository.findById(dto.getIdVet())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class, () -> appointmentService.createAppointment(dto));
    }

    @Test
    void createAppointmentResourceAlreadyExists() {
        //Arrange
        AppointmentRequestDto dto = new AppointmentRequestDto();
        dto.setIdPet(0);
        dto.setIdVet(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setTime(Time.valueOf("01:00:00"));
        dto.setReason("hi");

        Pet pet = new Pet();
        pet.setIdPet(0);

        Vet vet = new Vet();
        vet.setId(0);

        Appointment app = new Appointment();
        app.setIdAppointment(0);
        app.setPet(pet);
        app.setVet(vet);
        app.setDate(dto.getDate());
        app.setTime(dto.getTime());
        app.setReason(dto.getReason());
        app.setConfirm(false);

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.of(pet));
        when(vetRepository.findById(dto.getIdVet())).thenReturn(Optional.of(vet));
        when(appointmentRepository.existsByDate(dto.getDate())).thenReturn(true);
        //Act & Assert
        assertThrows(DuplicateResourceException.class, () -> appointmentService.createAppointment(dto));
    }

    @Test
    public void updateAppointmentConfirmation(){
        //Arrange
        int id = 0;

        Appointment app = new Appointment();
        app.setIdAppointment(0);
        app.setConfirm(false);

        when(appointmentRepository.findByIdAppointment(id)).thenReturn(Optional.of(app));
        when(appointmentRepository.save(app)).thenReturn(app);

        //Act
        Appointment result = appointmentService.updateAppointmentConfirmation(id);

        //Assert
        assertTrue(result.isConfirm());
    }

    @Test
    public void updateAppointmentConfirmationNotFound(){
        //Arrange
        int id = 0;

        when(appointmentRepository.findByIdAppointment(id)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class, () -> appointmentService.updateAppointmentConfirmation(id));
    }

    @Test
    public void getAppoinmentsByPetId(){
        //Arrange
        int id = 0;
        //Act
        when(appointmentService.getAppointmentsByPetId(id)).thenReturn(null);
        List<AppointmentResponseDto> result = appointmentService.getAppointmentsByPetId(id);
        //Assert
        assertNull(result);
    }
}
