package com.petpal.petpalservice.model.dto;

import lombok.Getter;
import lombok.Setter;

public class RecoverPasswordDto {
    @Getter
    private String ownerEmail;
    @Getter
    private String ownerPassword;
    @Getter
    private String vetEmail;
    @Getter
    private String vetPassword;
    @Getter
    private int ownerPhone;
    @Getter
    private int vetPhone;
    @Getter
    private String newPassword;
}
