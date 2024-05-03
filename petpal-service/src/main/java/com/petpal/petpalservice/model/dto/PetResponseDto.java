package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private int id;
    private String petName;
    private String petSpecies;
    private String petBreed;
    private int petAge;
    private String petSex;
}
