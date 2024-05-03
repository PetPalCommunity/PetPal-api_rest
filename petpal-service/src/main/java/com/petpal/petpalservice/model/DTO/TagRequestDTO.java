package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequestDTO {
    @NotBlank(message = "La tag debe tener un nombre")
    private String name;
}
