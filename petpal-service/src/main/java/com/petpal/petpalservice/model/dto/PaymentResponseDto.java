package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentResponseDto {
    private int id;
    private int amount;
    private String description;
    private Date date;
}