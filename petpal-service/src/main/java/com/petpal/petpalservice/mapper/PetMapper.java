package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.entity.Pet;
import lombok.AllArgsConstructor;
import com.petpal.petpalservice.model.dto.PetResponseDto;

import java.util.List;

@AllArgsConstructor
@Component
public class PetMapper {
    private final ModelMapper modelMapper;

    public Pet dtoToEntity(PetRequestDto dto) {
        return modelMapper.map(dto, Pet.class);
    }

    public PetResponseDto entityToDto(Pet pet) {
        return modelMapper.map(pet, PetResponseDto.class);
    }

    public List <PetResponseDto> entityToListDto(List<Pet> pets) {
        return pets.stream()
        .map(this::entityToDto)
        .toList();
    }
    
}
