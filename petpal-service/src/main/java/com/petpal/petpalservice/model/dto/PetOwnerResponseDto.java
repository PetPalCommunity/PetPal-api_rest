package com.petpal.petpalservice.model.dto;

public class PetOwnerResponseDto {
    private int ownerId;
    private  String ownerName;
    private int ownerAge;
    private String ownerSex;
    private String ownerEmail;
    private int ownerFollowers;
    private int ownerFollowed;
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
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
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
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
