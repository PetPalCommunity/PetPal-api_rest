package com.petpal.petpalservice.model.dto;

import java.time.DayOfWeek;
import java.util.Set;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderUpdateRequestDto {

    private String reminderName;
    private String reminderDescription;
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]", message = "El formato de la hora del reminder debe ser HH:mm:ss")
    private String reminderTime;
    private Set<DayOfWeek> days;
}
