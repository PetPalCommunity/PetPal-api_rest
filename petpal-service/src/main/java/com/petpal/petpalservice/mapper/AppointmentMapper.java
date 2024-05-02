package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.entity.Appointment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentMapper {
    private final ModelMapper modelMapper;

    public Appointment convertToEntity(AppointmentRequestDto appointmentRequestDto) {
        return modelMapper.map(appointmentRequestDto, Appointment.class);
    }

    public AppointmentResponseDto convertToDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    public List<AppointmentResponseDto> convertToDto(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::convertToDto)
                .toList();
    }
}

