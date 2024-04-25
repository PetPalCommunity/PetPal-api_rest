package com.petpal.petpalservice.mapper;


import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;

public class PetOwnerMapper {
    public static PetOwner dtoToEntity(PetOwnerRequestDto dto) {
        PetOwner petOwner = new PetOwner();
        petOwner.setOwnerName(dto.getOwnerName());
        petOwner.setOwnerAge(dto.getOwnerAge());
        petOwner.setOwnerSex(dto.getOwnerSex());
        petOwner.setOwnerPhone(dto.getOwnerPhone());
        petOwner.setOwnerEmail(dto.getOwnerEmail());
        return petOwner;
    }
}
