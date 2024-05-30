package com.petpal.petpalservice.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignInRequestDto {
    private String ownerEmail;
    private String ownerPassword;
    private String vetEmail;
    private String vetPassword;

}
