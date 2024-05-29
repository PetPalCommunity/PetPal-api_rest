package com.petpal.petpalservice.model.dto;

public class VisibilityRequestDto {
  private boolean profileVisible;
  private boolean postVisible;
  private boolean petVisible;

  public boolean isProfileVisible() {
    return profileVisible;
  }

  public void setProfileVisible(boolean profileVisible) {
    this.profileVisible = profileVisible;
  }

  public boolean isPostVisible() {
    return postVisible;
  }

  public void setPostVisible(boolean postVisible) {
    this.postVisible = postVisible;
  }

  public boolean isPetVisible() {
    return petVisible;
  }

  public void setPetVisible(boolean petVisible) {
    this.petVisible = petVisible;
  }
}
