package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOwnerProfileDTO {
    private String alias;
    private String firstname;
    private String lastname;
    private String image;
    private String description;
    private int followed;
    private int followers;
    private int age;
    private String sex;
    private String phone;
    private boolean profileVisible;
    private boolean postVisible;
    private boolean petVisible;
}
