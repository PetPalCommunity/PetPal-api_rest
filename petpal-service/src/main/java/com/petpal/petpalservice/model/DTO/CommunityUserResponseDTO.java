
package com.petpal.petpalservice.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityUserResponseDTO {
    private String role;
  private String username;
  private Long communityId;
}
