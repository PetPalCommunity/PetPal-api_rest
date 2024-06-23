package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.AppointmentRequestDTO;
import com.petpal.petpalservice.model.dto.AppointmentResponseDTO;
import com.petpal.petpalservice.model.dto.PaymentResponseDTO;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Payment;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentMapper {
    private final ModelMapper modelMapper;

    public Appointment convertToEntity(AppointmentRequestDTO appointmentRequestDto) {
        return modelMapper.map(appointmentRequestDto, Appointment.class);
    }

    public AppointmentResponseDTO convertToDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    public List<AppointmentResponseDTO> convertToListDto(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::convertToDto)
                .toList();
    }

    public PaymentResponseDTO convertToPaymentDto(Payment payment) {
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
}

