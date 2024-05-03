package com.petpal.petpalservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDto {
    private int id;
    
    @NotNull(message = "El id del dueño de la mascota no puede ser nulo")
    private int ownerId;
   
    @NotBlank(message = "El nombre de la mascota no puede estar vacío")
    private String petName;

    @NotBlank(message = "La especie de la mascota no puede estar vacía")
    private String petSpecies;

    @NotBlank(message = "La raza de la mascota no puede estar vacía")
    private String petBreed;

    @NotNull(message = "La edad de la mascota no puede ser nula")
    @DecimalMin(value = "0", message = "La edad de la mascota no puede ser negativa")
    private int petAge;

    @NotBlank(message = "El sexo de la mascota no puede estar vacío")
    private String petSex;
}


