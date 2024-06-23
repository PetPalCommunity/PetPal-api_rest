package com.petpal.petpalservice.model.dto;


import java.time.LocalDate;

import java.util.List;

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
  private Long countMembers;
  private List<String> tags;
  private LocalDate creationDate;
}

