package com.petpal.petpalservice.mapper;


import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;

public class PetOwnerMapper {
    public static PetOwner dtoToEntity(PetOwnerRequestDto dto) {
        PetOwner petOwner = new PetOwner();
        petOwner.setOwner_name(dto.getOwner_name());
        petOwner.setOwner_age(dto.getOwner_age());
        petOwner.setOwner_sex(dto.getOwner_sex());
        petOwner.setOwner_phone(dto.getOwner_phone());
        petOwner.setOwner_email(dto.getOwner_email());
        return petOwner;
    }
}
