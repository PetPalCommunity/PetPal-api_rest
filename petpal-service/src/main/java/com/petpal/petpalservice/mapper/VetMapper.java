package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Vet;


public class VetMapper {
    public static Vet dtoToEntity(VetRequestDto dto) {
        Vet vet = new Vet();
        vet.setVetName(dto.getVetName());
        vet.setVetLastname(dto.getVetLastname());
        vet.setVetSex(dto.getVetSex());
        vet.setVetPhone(dto.getVetPhone());
        vet.setVetLocation(dto.getVetLocation());
        vet.setVetEmail(dto.getVetEmail());
        vet.setVetPassword(dto.getVetPassword());
        vet.setVetLicenseNumber(dto.getVetLicenseNumber());
        return vet;
    }
}
