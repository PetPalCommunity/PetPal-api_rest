package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDTO {
    @NotNull(message = "El id del comentario no puede ser nulo")
    private Long commentId;

    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    private String content;
}
