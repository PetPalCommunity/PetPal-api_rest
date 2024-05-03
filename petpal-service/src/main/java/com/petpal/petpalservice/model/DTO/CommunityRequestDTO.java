package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityRequestDTO {
    @NotBlank(message = "Ingresa un nombre")
    private String name;
    @NotBlank(message = "Ingresa una descripcion")
    private String description;
    @NotNull(message = "Ingresa una cantidad")
    private Long countFollowers;

}
