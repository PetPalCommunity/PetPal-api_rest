package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetRequestDto {
  private String vetName;
  private String vetLastname;
  private String vetSex;
  private int vetPhone;
  private String vetLocation;
  private String vetEmail;
  private String vetPassword;
  private int vetLicenseNumber;
  public String getVetName() {
    return vetName;
  }
  public void setVetName(String vetName) {
    this.vetName = vetName;
  }
  public String getVetLastname() {
    return vetLastname;
  }
  public void setVetLastname(String vetLastname) {
    this.vetLastname = vetLastname;
  }
  public String getVetSex() {
    return vetSex;
  }
  public void setVetSex(String vetSex) {
    this.vetSex = vetSex;
  }
  public int getVetPhone() {
    return vetPhone;
  }
  public void setVetPhone(int vetPhone) {
    this.vetPhone = vetPhone;
  }
  public String getVetLocation() {
    return vetLocation;
  }
  public void setVetLocation(String vetLocation) {
    this.vetLocation = vetLocation;
  }
  public String getVetEmail() {
    return vetEmail;
  }
  public void setVetEmail(String vetEmail) {
    this.vetEmail = vetEmail;
  }
  public String getVetPassword() {
    return vetPassword;
  }
  public void setVetPassword(String vetPassword) {
    this.vetPassword = vetPassword;
  }
  public int getVetLicenseNumber() {
    return vetLicenseNumber;
  }
  public void setVetLicenseNumber(int vetLicenseNumber) {
    this.vetLicenseNumber = vetLicenseNumber;
  }
}
