package com.petpal.petpalservice.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long countFollowers;
}
