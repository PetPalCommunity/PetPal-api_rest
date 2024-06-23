package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordRequestDTO{
    
    @NotBlank(message = "El nombre de la mascota no puede estar vacio")
    private String petName;

    @NotBlank(message = "La fecha no puede estar vacia")
    @Pattern(regexp = "^([0-9]{4}-(0[1-9]|1[0-2])-0[1-9]|[12][0-9]|3[01])$", message = "La fecha debe tener el formato yyyy-mm-dd")
    String date;

    @NotBlank(message = "El tipo de registro no puede estar vacio")
    private String kind;

    @NotBlank(message = "La descripción no puede estar vacia")
    private String description;
}