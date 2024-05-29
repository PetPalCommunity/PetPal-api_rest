package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet_owners")
public class PetOwner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pet_owner")
  private int id;
  @Column(name = "name", nullable = false)
  private String ownerName;
  @Column(name = "sex", nullable = false)
  private String ownerSex;
  @Column(name = "age", nullable = false)
  private int ownerAge;
  @Column(name = "phone_numb", nullable = false, unique = true)
  private int ownerPhone;
  @Column(name = "email", nullable = false, unique = true)
  private String ownerEmail;
  @Column(name = "password", nullable = false)
  private String ownerPassword;
  @Column(name = "reputation")
  private float ownerReputation;
  @Column(name = "followers")
  private int ownerFollowers = 0;
  @Column(name = "followed")
  private int ownerFollowed = 0;
  // Visibility settings
  @Column(name = "profile_visible")
  private boolean profileVisible = true;
  @Column(name = "post_visible")
  private boolean postVisible = true;
  @Column(name = "pet_visible")
  private boolean petVisible = true;
  // Aditional information
  @Column(name = "description")
  private String ownerDescription;
  @Column(name = "location")
  private String ownerLocation;
  @Column(name = "full_name")
  private String ownerFullName;
  @Column(name = "image")
  private String ownerImage;
  @Column(name = "contact_info")
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
  public String getOwnerContactInfo() {
    return ownerContactInfo;
  }
  public void setOwnerContactInfo(String ownerContactInfo) {
    this.ownerContactInfo = ownerContactInfo;
  }
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
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getOwnerName() {
    return ownerName;
  }
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }
  public String getOwnerSex() {
    return ownerSex;
  }
  public void setOwnerSex(String ownerSex) {
    this.ownerSex = ownerSex;
  }
  public int getOwnerAge() {
    return ownerAge;
  }
  public void setOwnerAge(int ownerAge) {
    this.ownerAge = ownerAge;
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
  public String getOwnerImage() {
    return ownerImage;
  }
  public void setOwnerImage(String ownerImage) {
    this.ownerImage = ownerImage;
  }
  public float getOwnerReputation() {
    return ownerReputation;
  }
  public void setOwnerReputation(float ownerReputation) {
    this.ownerReputation = ownerReputation;
  }
  public int getOwnerFollowers() {
    return ownerFollowers;
  }
  public void setOwnerFollowers(int ownerFollowers) {
    this.ownerFollowers = ownerFollowers;
  }
  public int getOwnerFollowed() {
    return ownerFollowed;
  }
  public void setOwnerFollowed(int ownerFollowed) {
    this.ownerFollowed = ownerFollowed;
  }
}
