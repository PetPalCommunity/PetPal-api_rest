package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Vet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVetRequestDto {
    @NotBlank(message = "El id del vet no puede estar vacio")
    private int idVet;
    @NotBlank(message = "El id del pet owner no puede estar vacio")
    private int idPetOwner;
    @NotBlank(message = "Las estrellas no pueden estar vacias")
    @Size(min = 1, max = 5, message = "Las estrellas deben estar entre 1 y 5")
    private BigDecimal stars;
    @NotBlank(message = "El comentario no puede estar vacio")
    private String comment;
}