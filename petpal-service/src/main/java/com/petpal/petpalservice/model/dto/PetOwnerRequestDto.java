package com.petpal.petpalservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOwnerRequestDto {
    private  String owner_name;
    private int owner_age;
    private String owner_sex;
    private int owner_phone;
    private String owner_email;
    private String owner_password;
}
