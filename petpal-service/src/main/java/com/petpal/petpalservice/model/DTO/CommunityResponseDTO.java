package com.petpal.petpalservice.model.DTO;


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
  private Long countFollowers;
  private List<String> tags;
  private LocalDate creationDate;
  private List<CommunityUserResponseDTO> communityUsers;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
  public List<String> getTags() {
    return tags;
  }
  public void setTags(List<String> tags) {
    this.tags = tags;
  }
  public LocalDate getCreationDate() {
    return creationDate;
  }
  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }
  public List<CommunityUserResponseDTO> getCommunityUsers() {
    return communityUsers;
  }
  public void setCommunityUsers(List<CommunityUserResponseDTO> communityUsers) {
    this.communityUsers = communityUsers;
  }

}
