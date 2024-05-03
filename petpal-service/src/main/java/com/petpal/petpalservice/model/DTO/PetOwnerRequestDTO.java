package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOwnerRequestDTO {
    @NotBlank(message = "El dueño no puede tener nombre vacío")
    private String userName;
}
