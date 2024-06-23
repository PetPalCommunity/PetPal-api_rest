package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String firstname;
    private String lastname;
    private String alias;
    private String role;
    private boolean enabled;
    private boolean profileVisible;
    private boolean postVisible;
    private boolean petVisible;
}
