package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    private String firstname;

    @NotBlank(message = "El apellido del usuario no puede estar vacio")
    private String lastname;

    @NotBlank(message = "El alias del usuario no puede estar vacio")
    private String alias;

    @NotBlank(message = "El email del usuario no puede estar vacio")
    @Email
    private String email;

    @NotBlank(message = "La contraseña del usuario no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El número de teléfono del usuario no puede estar vacio")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}