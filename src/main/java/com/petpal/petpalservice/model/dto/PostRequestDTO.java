package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    @NotBlank(message = "El nombre de la comunidad no puede estar vacío")
    private String communityName;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;
}
