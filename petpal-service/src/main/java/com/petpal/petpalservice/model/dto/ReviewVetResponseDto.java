package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Vet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReviewVetResponseDto {
    private int idReview;
    private int idVet;
    private int idPet;
    private BigDecimal stars;
    private String comment;
}
