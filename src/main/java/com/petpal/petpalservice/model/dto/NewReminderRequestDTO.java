package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReminderRequestDTO {
    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    private String petName;

    @NotBlank(message = "El nombre del recordatorio no puede estar vacío")
    private String name;

    @NotBlank(message = "La descripción del recordatorio no puede estar vacía")
    private String description;

    @NotBlank(message = "La fecha del recordatorio no puede estar vacía")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]", message = "El formato de la hora del reminder debe ser HH:mm:ss")
    private String time;

    @NotEmpty(message = "El día del recordatorio no puede estar vacío")
    private Set<DayOfWeek> days;

    
}


