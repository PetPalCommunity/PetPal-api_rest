package com.petpal.petpalservice.model.dto;

import lombok.Getter;

public class SignInRequestDto {
  @Getter
  private String ownerEmail;
  @Getter
  private String ownerPassword;
  @Getter
  private String vetEmail;
  @Getter
  private String vetPassword;
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
}
