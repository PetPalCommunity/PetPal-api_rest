package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.model.dto.*;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.ReviewVetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewVetService {
    private final ReviewVetRepository reviewVetRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final VetRepository vetRepository;

    public ReviewVetService(ReviewVetRepository reviewVetRepository, PetOwnerRepository petOwnerRepository, VetRepository vetRepository) {
        this.reviewVetRepository = reviewVetRepository;
        this.petOwnerRepository = petOwnerRepository;
        this.vetRepository = vetRepository;
    }

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
}