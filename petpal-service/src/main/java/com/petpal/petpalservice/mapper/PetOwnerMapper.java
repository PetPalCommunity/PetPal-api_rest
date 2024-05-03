package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.DTO.PetOwnerRequestDTO;
import com.petpal.petpalservice.model.DTO.PetOwnerResponseDTO;
import com.petpal.petpalservice.model.entities.PetOwner;
import java.util.List;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PetOwnerMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public PetOwner convertToEntity(PetOwnerRequestDTO PetOwnerRequestDTO){
        return modelMapper.map(PetOwnerRequestDTO, PetOwner.class);
    }

    public PetOwnerResponseDTO convertToDTO(PetOwner PetOwner){
        return modelMapper.map(PetOwner, PetOwnerResponseDTO.class);
    }

    public List<PetOwnerResponseDTO> convertToListDTO(List<PetOwner> communities){
        return communities.stream()
        .map(this::convertToDTO)
        .toList();
    }
    
    
}
