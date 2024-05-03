package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentRequestDto {
    @NotBlank(message = "El id de la cita no puede estar vacio")
    private int idAppointment;
    @NotBlank(message = "El monto del pago no puede estar vacio")
    private int amount;
    @NotBlank(message = "La razon del pago no puede estar vacia")
    private String description;
    @NotBlank(message = "La fecha del pago no puede estar vacia")
    @DateTimeFormat
    private Date date;
}

