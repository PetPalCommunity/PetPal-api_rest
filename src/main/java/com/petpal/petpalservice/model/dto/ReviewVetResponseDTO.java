package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReviewVetResponseDTO {
    private Long id;

    private String aliasOwner;

    private Double stars;

    private String comment;
}
