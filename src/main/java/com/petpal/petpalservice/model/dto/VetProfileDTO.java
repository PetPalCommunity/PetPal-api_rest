package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetProfileDTO {
    private String alias;
    private String firstname;
    private String lastname;
    private String image;
    private int age;
    private String sex;
    private String description;
    private double reputation;
    private String speciality;
    private String location;
    private String phone;
    private boolean profileVisible;
    private boolean postVisible;
  
}
