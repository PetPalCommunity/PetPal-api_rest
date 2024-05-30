package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.ReviewVetMapper;
import com.petpal.petpalservice.model.dto.*;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.ReviewVetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewVetService {
    private final ReviewVetRepository reviewVetRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final VetRepository vetRepository;
    private final ReviewVetMapper reviewVetMapper;

    public ReviewVet createReview(ReviewVetRequestDto dto) {
        Optional<PetOwner> optionalPetOwner = petOwnerRepository.findById(dto.getIdPetOwner());
        if (optionalPetOwner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }

        Optional<Vet> optionalVet = vetRepository.findById(dto.getIdVet());
        if (optionalVet.isEmpty()) {
            throw new NotFoundException("Vet not found");
        }

        ReviewVet reviewVet = new ReviewVet();
        reviewVet.setPetOwner(optionalPetOwner.get());
        reviewVet.setVet(optionalVet.get());
        reviewVet.setStars(dto.getStars());
        reviewVet.setComment(dto.getComment());
        return reviewVetRepository.save(reviewVet);
    }

    @Transactional
    public List<ReviewVetResponseDto> getReviewsByVetId(int idVet) {
        List<ReviewVet> reviews = reviewVetRepository.findByVetId(idVet);
        return reviewVetMapper.convertToListDto(reviews);
    }
}