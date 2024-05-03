package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.Pet;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentRequestDto{
    @NotBlank(message = "El id del veterinario no puede estar vacio")
    private int idVet;
    @NotBlank(message = "El id de la mascota no puede estar vacio")
    private int idPet;
    @NotBlank(message = "La fecha no puede estar vacia")
    @DateTimeFormat
    @Future
    private Date date;
    @NotBlank(message = "La hora no puede estar vacia")
    private Time time;
    @NotBlank(message = "La razon de la consulta no puede estar vacia")
    private String reason;
    private boolean confirm;
}

