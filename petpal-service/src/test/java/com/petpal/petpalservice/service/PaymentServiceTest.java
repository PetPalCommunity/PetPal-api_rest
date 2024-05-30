package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.AppointmentMapper;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.dto.PaymentRequestDto;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Payment;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PaymentRepository;
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
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void createPayment(){
        //Arrange
        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setIdAppointment(0);
        dto.setAmount(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setDescription("hi");

        Appointment appointment = new Appointment();
        appointment.setIdAppointment(0);

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate());
        payment.setDescription(dto.getDescription());
        payment.setAppointment(appointment);

        when(appointmentRepository.findByIdAppointment(0)).thenReturn(Optional.of(appointment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        //Act
        Payment result = paymentService.createPayment(dto);

        //Assert
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(appointment, result.getAppointment());
    }

    @Test
    public void createPayment_AppointmentNotFound(){
        //Arrange
        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setIdAppointment(0);
        dto.setAmount(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setDescription("hi");

        when(appointmentRepository.findByIdAppointment(0)).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThrows(NotFoundException.class, () -> paymentService.createPayment(dto));
    }
}

