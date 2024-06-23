package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.CommunityRequestDTO;
import com.petpal.petpalservice.model.dto.CommunityResponseDTO;
import com.petpal.petpalservice.model.entity.Community;
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
    //Funcion para desconcatenar los tags
    String[] tags = community.getTags().split("\\$");
    CommunityResponseDTO communityResponseDTO = modelMapper.map(community, CommunityResponseDTO.class);
    communityResponseDTO.setTags(List.of(tags));
    return communityResponseDTO;
  }

  public List<CommunityResponseDTO> convertToListDTO(List<Community> communities){
    return communities.stream()
    .map(this::convertToDTO)
    .toList();
  }


}
