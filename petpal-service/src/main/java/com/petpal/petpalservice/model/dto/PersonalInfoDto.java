package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoDto {
  private String ownerDescription;
  private String ownerLocation;
  private String ownerFullName;
  private String ownerImage;
  private String ownerContactInfo;
  public String getOwnerDescription() {
    return ownerDescription;
  }
  public void setOwnerDescription(String ownerDescription) {
    this.ownerDescription = ownerDescription;
  }
  public String getOwnerLocation() {
    return ownerLocation;
  }
  public void setOwnerLocation(String ownerLocation) {
    this.ownerLocation = ownerLocation;
  }
  public String getOwnerFullName() {
    return ownerFullName;
  }
  public void setOwnerFullName(String ownerFullName) {
    this.ownerFullName = ownerFullName;
  }
  public String getOwnerImage() {
    return ownerImage;
  }
  public void setOwnerImage(String ownerImage) {
    this.ownerImage = ownerImage;
  }
  public String getOwnerContactInfo() {
    return ownerContactInfo;
  }
  public void setOwnerContactInfo(String ownerContactInfo) {
    this.ownerContactInfo = ownerContactInfo;
  }
}
