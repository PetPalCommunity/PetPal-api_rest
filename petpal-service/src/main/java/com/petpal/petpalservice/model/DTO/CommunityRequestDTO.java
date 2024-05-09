package com.petpal.petpalservice.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
// @AllArgsConstructor
@NoArgsConstructor
public class CommunityRequestDTO {
	@NotBlank(message = "Ingresa un nombre")
    private String name;
    @NotBlank(message = "Ingresa una descripcion")
    private String description;
    @NotNull(message = "Ingresa una cantidad")
    private Long countFollowers;
    
    private List<String> tags;


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
	public Long getCountFollowers() {
		return countFollowers;
	}
	public void setCountFollowers(Long countFollowers) {
		this.countFollowers = countFollowers;
	}

}
