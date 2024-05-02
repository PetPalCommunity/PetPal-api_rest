package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentResponseDto{
    private int id;
    private String vetName;
    private int paymentAmount;
    private String petName;
    private Date date;
    private Time time;
    private String reason;
}
