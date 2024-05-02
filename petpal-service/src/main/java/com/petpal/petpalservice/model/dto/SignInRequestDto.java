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
}
