package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.ReviewVetResponseDto;
import com.petpal.petpalservice.model.entity.ReviewVet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewVetMapper{

    private final ModelMapper modelMapper;

    public ReviewVetResponseDto convertToDto(ReviewVet review) {
        return modelMapper.map(review, ReviewVetResponseDto.class);
    }
    public List<ReviewVetResponseDto> convertToListDto(List<ReviewVet> reviews) {
        return reviews.stream()
                .map(this::convertToDto)
                .toList();
    }
}
