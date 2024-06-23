package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    @NotNull(message = "El id de la cita es requerido")
    Long idAppointment;

    @NotBlank(message = "El nombre del titular es requerido")
    String cardHolder;

    @NotBlank(message = "El número de tarjeta es requerido")
    @Pattern(regexp = "^[0-9]{16}$", message = "El número de tarjeta debe tener 16 dígitos")
    String cardNumber;

    @NotBlank(message = "La fecha de expiración es requerida")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/[0-9]{2}$", message = "La fecha de expiración debe tener el formato MM/YY")
    String expirationDate;

    @NotBlank(message = "El código de seguridad es requerido")
    @Pattern(regexp = "^[0-9]{3}$", message = "El código de seguridad debe tener 3 dígitos")
    String cvv;

}
