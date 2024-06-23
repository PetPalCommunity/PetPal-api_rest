package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exceptions.BadRequestException;
import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.AppointmentMapper;
import com.petpal.petpalservice.model.dto.AppointmentRequestDTO;
import com.petpal.petpalservice.model.dto.AppointmentResponseDTO;
import com.petpal.petpalservice.model.dto.AppointmentUpdateDTO;
import com.petpal.petpalservice.model.dto.PaymentAPIResponseDTO;
import com.petpal.petpalservice.model.dto.PaymentRequestDTO;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Payment;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.model.enums.AppointmentStatus;
import com.petpal.petpalservice.model.enums.PaymentStatus;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PaymentRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final PaymentRepository paymentRepository;

    private final PetOwnerService petOwnerService;
    private final VetService vetService;
    private final MockPaymentService mockPaymentService;

    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentDto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(), appointmentDto.getPetName())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario " + owner.getAlias() + " no tiene una mascota llamada " + appointmentDto.getPetName()));
        Vet vet = vetRepository.findByAlias(appointmentDto.getAlias())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado al veterinario " + appointmentDto.getAlias()));
        Appointment appointment = new Appointment();
        appointment.setPet(pet);
        appointment.setVet(vet);
        appointment.setDate(LocalDate.parse(appointmentDto.getDate()));
        appointment.setStartTime(LocalTime.parse(appointmentDto.getStartTime()));
        appointment.setEndTime(LocalTime.parse(appointmentDto.getEndTime()));
        appointment.setReason(appointmentDto.getReason());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointmentRepository.save(appointment);
        return appointmentMapper.convertToDto(appointment);
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByPet(String petName) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(), petName)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario " + owner.getAlias() + " no puede acceder a la mascota " + petName));
        List<Appointment> appointments = appointmentRepository.findByPetId(pet.getId());
        return appointmentMapper.convertToListDto(appointments);
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByVet(String alias) {
        Vet vet = vetService.getCurrentVet();
        List<Appointment> appointments = appointmentRepository.findByVetId(vet.getId());
        return appointmentMapper.convertToListDto(appointments);
    }

    @Transactional
    public AppointmentResponseDTO updateAppoinmentStatus(AppointmentUpdateDTO appointmentUpdateDTO) {
        Vet vet = vetService.getCurrentVet();
        Appointment appointment = appointmentRepository.findByIdAndVetId(appointmentUpdateDTO.getId(), vet.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No puedes modificar la cita que pertenece a " 
                                                                + "otro veterinario"));
        appointment.setStatus(AppointmentStatus.valueOf(appointmentUpdateDTO.getStatus().name()));
        appointmentRepository.save(appointment);
        AppointmentResponseDTO appointmentResponseDTO = appointmentMapper.convertToDto(appointment);
        if (appointment.getStatus() == AppointmentStatus.CONFIRMED){
            Payment payment = new Payment();
            payment.setAppointment(appointment);
            payment.setAmount(BigDecimal.valueOf(appointmentUpdateDTO.getAmount()));
            payment.setStatus(PaymentStatus.PENDING);
            paymentRepository.save(payment);
            appointmentResponseDTO.setPayment(appointmentMapper.convertToPaymentDto(payment));
        }
        return appointmentResponseDTO;
    }

    @Transactional
    public void deleteAppointment(String petName,Long id) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(), petName)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario " + owner.getAlias() 
                                                                + " no puede acceder a la mascota " + petName));
        Appointment appointment = appointmentRepository.findByIdAndPetId(id, pet.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la cita con id " + id 
                                                               + " para la mascota " + petName));
        if(appointment.getStatus() == AppointmentStatus.CONFIRMED){
            throw new ResourceNotFoundException("No puedes eliminar una cita confirmada");
        }
        appointmentRepository.delete(appointment);
    }

    @Transactional    
    public AppointmentResponseDTO payAppointment(String petName,PaymentRequestDTO paymentDto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Pet pet = petRepository.findByPetOwnerAliasAndPetName(owner.getAlias(), petName)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario " + owner.getAlias() 
                                                                + " no puede acceder a la mascota " + petName));
        Appointment appointment = appointmentRepository.findByIdAndPetId(paymentDto.getIdAppointment(), pet.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la cita con id " 
                                                                + paymentDto.getIdAppointment() + " para la mascota " + petName));
        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
                throw new ResourceNotFoundException("La cita con id " + paymentDto.getIdAppointment() + 
                                                        " para la mascota " + petName + " no está confirmada");
        }
        Payment payment = paymentRepository.findByAppointmentId(appointment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado un pago para la cita con id " 
                                                                + paymentDto.getIdAppointment()));
        if (payment.getStatus().equals(PaymentStatus.PAID)) {
            throw new BadRequestException("La cita con id " + paymentDto.getIdAppointment() + 
                                                " para la mascota " + petName + " ya ha sido pagada");
        }
        PaymentAPIResponseDTO paymentAPIResponseDTO = mockPaymentService.processPayment(paymentDto);
        if (paymentAPIResponseDTO.isSuccess()) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setVoucherNumber(paymentAPIResponseDTO.getTransactionId());
            paymentRepository.save(payment);
        } else {
            payment.setStatus(PaymentStatus.REJECTED);
            paymentRepository.save(payment);
        }
        payment.setDate(LocalDate.now());
        payment.setTime(LocalTime.now());
        AppointmentResponseDTO appointmentResponseDTO = appointmentMapper.convertToDto(appointment);
        appointmentResponseDTO.setPayment(appointmentMapper.convertToPaymentDto(payment));
        return appointmentResponseDTO;
    }
}