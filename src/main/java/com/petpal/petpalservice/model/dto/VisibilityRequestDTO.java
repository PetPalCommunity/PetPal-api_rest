package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisibilityRequestDTO {
    
    @NotNull(message = "La visibilidad del perfil no puede ser nula")
    boolean profileVisible;

    @NotNull(message = "La visibilidad de las publicaciones no puede ser nula")
    boolean postVisible;

    @NotNull(message = "La visibilidad de las mascotas no puede ser nula")
    boolean petVisible;
}
