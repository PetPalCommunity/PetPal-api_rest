package com.petpal.petpalservice.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.ReviewVetResponseDTO;
import com.petpal.petpalservice.model.dto.VetProfileDTO;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.model.entity.Vet;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VetMapper {
    private final ModelMapper modelMapper;

    public VetProfileDTO convertToDTO(Vet vet){
        return  modelMapper.map(vet, VetProfileDTO.class);
    }

    public List<VetProfileDTO> convertToDTOList(List<Vet> vetList){
        return vetList.stream().map(this::convertToDTO).toList();
    }

    public ReviewVetResponseDTO convertToReviewDTO(ReviewVet reviewVet){
        ReviewVetResponseDTO reviewVetResponseDTO =  modelMapper.map(reviewVet, ReviewVetResponseDTO.class);
        reviewVetResponseDTO.setAliasOwner(reviewVet.getPetOwner().getAlias());
        return reviewVetResponseDTO;
    }

    public List<ReviewVetResponseDTO> convertToReviewDTOList(List<ReviewVet> reviewVetList){
        return reviewVetList.stream().map(this::convertToReviewDTO).toList();
    }
}
