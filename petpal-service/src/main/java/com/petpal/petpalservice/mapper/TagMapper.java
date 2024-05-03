package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.DTO.TagRequestDTO;
import com.petpal.petpalservice.model.DTO.TagResponseDTO;
import com.petpal.petpalservice.model.entities.Tag;
import java.util.List;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TagMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Tag convertToEntity(TagRequestDTO TagRequestDTO){
        return modelMapper.map(TagRequestDTO, Tag.class);
    }

    public TagResponseDTO convertToDTO(Tag Tag){
        return modelMapper.map(Tag, TagResponseDTO.class);
    }

    public List<TagResponseDTO> convertToListDTO(List<Tag> communities){
        return communities.stream()
        .map(this::convertToDTO)
        .toList();
    }
    
    
}

