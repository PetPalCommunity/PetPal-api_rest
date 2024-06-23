package com.petpal.petpalservice.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.FollowerResponseDTO;
import com.petpal.petpalservice.model.dto.PetOwnerProfileDTO;
import com.petpal.petpalservice.model.entity.PetOwner;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PetOwnerMapper {
    private final ModelMapper modelMapper;

    public PetOwnerProfileDTO convertToDTO(PetOwner petOwner){
        return  modelMapper.map(petOwner, PetOwnerProfileDTO.class);
    }
    public FollowerResponseDTO convertToDTOFollower(PetOwner petOwner){
        return modelMapper.map(petOwner, FollowerResponseDTO.class);
    }
    public List<FollowerResponseDTO> convertToDTOFollowerList(List<PetOwner> petOwners){
        return petOwners.stream()
                .map(this::convertToDTOFollower)
                .toList();
    }

}
