package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String nextDate;
    private String time;
    private String days;
}
