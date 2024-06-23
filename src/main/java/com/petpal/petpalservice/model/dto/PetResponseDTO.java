package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDTO {
    private String name;
    private String species;
    private String breed;
    private int age;
    private String sex;
    private String image;
}
