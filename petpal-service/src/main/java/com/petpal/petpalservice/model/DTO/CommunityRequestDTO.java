package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class CommunityRequestDTO {
  @NotBlank(message = "Ingresa un nombre")
  private String name = "";
  @NotBlank(message = "Ingresa una descripcion")
  private String description = "";

  //set tags to empty list
  private List<String> tags = List.of();


  public List<String> getTags() {
    return tags;
  }
  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

}
