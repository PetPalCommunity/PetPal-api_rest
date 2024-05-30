package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.AppointmentMapper;
import com.petpal.petpalservice.model.dto.*;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final AppointmentMapper appointmentMapper;

    public Appointment createAppointment(AppointmentRequestDto appointmentDto) {
        Optional<Pet> optionalPet = petRepository.findById(appointmentDto.getIdPet());
        if (optionalPet.isEmpty()) {
            throw new NotFoundException("Pet not found");
        }

        Optional<Vet> optionalVet = vetRepository.findById(appointmentDto.getIdVet());
        if (optionalVet.isEmpty()) {
            throw new NotFoundException("Vet not found");
        }

        if (appointmentRepository.existsByDate(appointmentDto.getDate()) ||
                appointmentRepository.existsByReason(appointmentDto.getReason())) {
            throw new DuplicateResourceException("Resource already exists");
        }

        Appointment appointment = new Appointment();
        appointment.setPet(optionalPet.get());
        appointment.setVet(optionalVet.get());
        appointment.setTime(appointmentDto.getTime());
        appointment.setDate(appointmentDto.getDate());
        appointment.setReason(appointmentDto.getReason());
        appointment.setConfirm(false);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointmentConfirmation(AppointmentResponseDto dto) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAppointment(dto.getId());
        if (optionalAppointment.isEmpty()) {
            throw new NotFoundException("Appointment not found");
        }
        Appointment appointment = optionalAppointment.get();
        appointment.setConfirm(true);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public List<AppointmentResponseDto> getAllAppointments(){
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentMapper.convertToListDto(appointments);
    }

    public List<AppointmentResponseDto>getAppointmentsByPetId(int id){
        List<Appointment> appointments = appointmentRepository.findByPetId(id);
        return appointmentMapper.convertToListDto(appointments);
    }
}