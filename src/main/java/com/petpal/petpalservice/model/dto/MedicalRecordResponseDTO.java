package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponseDTO {
    private Long idPet;

    private Long id;

    private String kind;

    private String description;

    private String date;
}
