package com.petpal.petpalservice.model.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVetRequestDTO {
    
    @NotBlank(message = "El alias del veterinario no puede estar vacio")
    private String aliasVet;
    
    @NotNull(message = "Las estrellas no pueden estar vacias")
    @Range(min = 1, max = 5, message = "Las estrellas deben estar entre 1 y 5")
    private Double stars;
    
    @NotBlank(message = "El comentario no puede estar vacio")
    private String comment;
}