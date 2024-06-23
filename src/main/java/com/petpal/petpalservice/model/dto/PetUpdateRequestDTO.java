package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.DecimalMin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequestDTO {   

    private String name;
    
    private String pecies;

    private String breed;

    @DecimalMin(value = "0", message = "La edad de la mascota no puede ser negativa")
    private int age;

    private String sex;

    private String image;
}
