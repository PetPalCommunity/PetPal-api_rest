package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOwnerRequestDto {
    private  String ownerName;
    private int ownerAge;
    private String ownerSex;
    private int ownerPhone;
    private String ownerEmail;
    private String ownerPassword;
}