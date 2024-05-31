
package com.petpal.petpalservice.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityUserResponseDTO {
  // public CommunityUserResponseDTO(String userName2, String role2, Long id2) {
  //   this.username = userName2;
  //   this.role = role2;
  //   this.communityId = id2;
  // }
  private String username;
  private String role;
  private Long communityId;
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public Long getCommunityId() {
    return communityId;
  }
  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
  }
}

