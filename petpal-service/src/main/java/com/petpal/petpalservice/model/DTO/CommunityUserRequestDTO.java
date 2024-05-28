package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityUserRequestDTO {
    @NotBlank(message = "Debe tener un rol")
    private String role;
    @NotNull
    private String userName;
}
