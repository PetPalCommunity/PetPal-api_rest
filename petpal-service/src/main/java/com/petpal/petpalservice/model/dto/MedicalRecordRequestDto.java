package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.Pet;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.sql.Time;

@Data
@RequiredArgsConstructor

public class MedicalRecordRequestDto{
    @NotBlank(message = "La fecha no puede estar vacia")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    @NotBlank(message = "El id de la mascota no puede estar vacio")
    private int idPet;
    @NotBlank(message = "El tipo de registro no puede estar vacio")
    private String kind;
    @NotBlank(message = "La descripción no puede estar vacia")
    private String description;
}