package com.petpal.petpalservice.model.dto;

public class SignInRequestDto {
    private String ownerEmail;
    private String ownerPassword;

    public String getOwnerEmail() {
        return ownerEmail;
    }
    public String getOwnerPassword() {
        return ownerPassword;
    }
}
