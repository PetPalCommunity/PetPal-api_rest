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
}
