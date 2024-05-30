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

public class DocumentRequestDto{
    @NotBlank(message = "El id de la anotación no puede estar vacia")
    private int idRecord;
    @NotBlank(message = "El link del documento no puede estar vacio")
    String link;
}