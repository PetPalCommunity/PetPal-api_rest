package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetProfileUpdateDTO {
    private String firstame;

    private String lastname;

    private String image;

    private String description;

    private String sex;

    @Min(value = 1, message = "La edad debe ser mayor a 18")
    private int age;

    private String phoneNumber;

    private String speciality;

    private String location;

    


}
