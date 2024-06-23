package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.enums.AppointmentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateDTO {
    @NotNull(message = "El id de la cita no puede ser nulo")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @NotNull(message = "El monto de la cita no puede ser nulo")
    private Double amount;
}
