package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto{

    private Date date;
    private Time time;
    private String reason;
}