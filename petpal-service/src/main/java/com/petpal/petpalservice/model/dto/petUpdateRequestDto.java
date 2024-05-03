package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.DecimalMin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class petUpdateRequestDto {    
    private String petName;
    private String petSpecies;

    private String petBreed;
    @DecimalMin(value = "0", message = "La edad de la mascota no puede ser negativa")
    private int petAge;

    private String petSex;

    private String image;
}
