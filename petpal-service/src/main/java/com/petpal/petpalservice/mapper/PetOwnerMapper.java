package com.petpal.petpalservice.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.entity.PetOwner;

// import lombok.AllArgsConstructor;

@Component
// @AllArgsConstructor
public class PetOwnerMapper {
  private final ModelMapper modelMapper;
  public PetOwnerMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }
  public PetOwnerMapper() {
    this.modelMapper = new ModelMapper();
  }
public static PetOwner dtoToEntity(PetOwnerRequestDto dto) {
    PetOwner petOwner = new PetOwner();
    petOwner.setOwnerName(dto.getOwnerName());
    petOwner.setOwnerAge(dto.getOwnerAge());
    petOwner.setOwnerSex(dto.getOwnerSex());
    petOwner.setOwnerPhone(dto.getOwnerPhone());
    petOwner.setOwnerEmail(dto.getOwnerEmail());
    return petOwner;
  }
  public PetOwnerResponseDto entityToDto(PetOwner petOwner) {
    return modelMapper.map(petOwner, PetOwnerResponseDto.class);
  }
}
