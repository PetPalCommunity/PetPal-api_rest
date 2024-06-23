package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentResponseDTO{
    private int id;
    private String vetAlias;
    private String petName;
    private String date;
    private String startTime;
    private String endTime;
    private String reason;
    private String status;
    private PaymentResponseDTO payment;
}
