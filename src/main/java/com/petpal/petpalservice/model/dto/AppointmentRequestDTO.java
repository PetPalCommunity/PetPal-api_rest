package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO{

    @NotBlank(message = "El alias del veterinario no puede estar vacio")
    private String alias;

    @NotBlank(message = "El nombre de la mascota no puede estar vacio")
    private String petName;

    @NotBlank(message = "La fecha no puede estar vacia")
    @Pattern(regexp = "^([0-9]{4}-(0[1-9]|1[0-2])-0[1-9]|[12][0-9]|3[01])$", message = "La fecha debe tener el formato yyyy-mm-dd")
    private String date;

    @NotBlank(message = "La hora de inicio no puede estar vacia")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "La hora de inicio debe tener el formato HH:MM:SS")
    private String startTime;

    @NotBlank(message = "La hora de fin no puede estar vacia")
    private String endTime;

    @NotBlank(message = "La razon de la consulta no puede estar vacia")
    private String reason;
}

