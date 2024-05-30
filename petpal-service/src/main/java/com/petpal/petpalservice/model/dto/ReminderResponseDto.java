package com.petpal.petpalservice.model.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderResponseDto {
    private String reminderName;
    private String reminderDescription;
    private String nextReminderDate;
    private String reminderTime;
    private String days;
    private int idPet;

    public String getReminderTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(this.reminderTime);
        return time.format(formatter);
    }
}
