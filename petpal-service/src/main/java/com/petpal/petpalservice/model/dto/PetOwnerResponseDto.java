package com.petpal.petpalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PetOwnerResponseDto {
    private int ownerId;
    private  String ownerName;
    private int ownerAge;
    private String ownerSex;
    private String ownerEmail;
    private int ownerFollowers;
    private int ownerFollowed;
}