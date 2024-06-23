package com.petpal.petpalservice.model.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityRequestDTO {

  @NotBlank(message = "Ingresa un nombre")
  private String name = "";

  @NotBlank(message = "Ingresa una descripcion")
  private String description = "";

  @NotEmpty(message = "Ingresa al menos un tag")
  private List<String> tags;

}
