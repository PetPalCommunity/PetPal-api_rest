package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserResponseDTO;
import com.petpal.petpalservice.model.entities.CommunityUser;
import java.util.List;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommunityUserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public CommunityUser convertToEntity(CommunityUserRequestDTO CommunityUserRequestDTO){
        return modelMapper.map(CommunityUserRequestDTO, CommunityUser.class);
    }

    public CommunityUserResponseDTO convertToDTO(CommunityUser CommunityUser){
        return modelMapper.map(CommunityUser, CommunityUserResponseDTO.class);
    }

    public List<CommunityUserResponseDTO> convertToListDTO(List<CommunityUser> communities){
        return communities.stream()
        .map(this::convertToDTO)
        .toList();
    }
    
    
}
