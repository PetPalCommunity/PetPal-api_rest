package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOwnerRequestDto {
  private  String ownerName;
  private int ownerAge;
  private String ownerSex;
  private int ownerPhone;
  private String ownerEmail;
  private String ownerPassword;
  public String getOwnerName() {
    return ownerName;
  }
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }
  public int getOwnerAge() {
    return ownerAge;
  }
  public void setOwnerAge(int ownerAge) {
    this.ownerAge = ownerAge;
  }
  public String getOwnerSex() {
    return ownerSex;
  }
  public void setOwnerSex(String ownerSex) {
    this.ownerSex = ownerSex;
  }
  public int getOwnerPhone() {
    return ownerPhone;
  }
  public void setOwnerPhone(int ownerPhone) {
    this.ownerPhone = ownerPhone;
  }
  public String getOwnerEmail() {
    return ownerEmail;
  }
  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }
  public String getOwnerPassword() {
    return ownerPassword;
  }
  public void setOwnerPassword(String ownerPassword) {
    this.ownerPassword = ownerPassword;
  }
}
