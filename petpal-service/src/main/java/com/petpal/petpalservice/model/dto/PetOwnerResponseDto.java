package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOwnerResponseDto {
  private int ownerId;
  private  String ownerName;
  private int ownerAge;
  private String ownerSex;
  private String ownerEmail;
  private int ownerFollowers;
  private int ownerFollowed;
  private String ownerDescription;
  private String ownerLocation;
  private String ownerFullName;
  private String ownerImage;
  private String ownerContactInfo;
}
