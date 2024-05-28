package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.model.entities.Community;
import java.util.List;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommunityMapper {
  private final ModelMapper modelMapper = new ModelMapper();

  public Community convertToEntity(CommunityRequestDTO communityRequestDTO){
    return modelMapper.map(communityRequestDTO, Community.class);
  }

  public CommunityResponseDTO convertToDTO(Community community){
    return modelMapper.map(community, CommunityResponseDTO.class);
  }

  public List<CommunityResponseDTO> convertToListDTO(List<Community> communities){
    return communities.stream()
    .map(this::convertToDTO)
    .toList();
  }


}
