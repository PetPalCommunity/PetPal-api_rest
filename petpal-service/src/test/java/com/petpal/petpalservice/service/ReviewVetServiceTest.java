package com.petpal.petpalservice.service;

import com.petpal.petpalservice.mapper.ReviewVetMapper;
import com.petpal.petpalservice.model.dto.ReviewVetRequestDto;
import com.petpal.petpalservice.model.dto.ReviewVetResponseDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.ReviewVetRepository;
import com.petpal.petpalservice.repository.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ReviewVetServiceTest {
    @InjectMocks
    private ReviewVetService reviewVetService;
    @Mock
    private ReviewVetRepository reviewVetRepository;
    @Mock
    private PetOwnerRepository petOwnerRepository;
    @Mock
    private VetRepository vetRepository;
    @Mock
    private ReviewVetMapper reviewVetMapper;
    @Test
    public void createReview() {
        //Arrange
        ReviewVetRequestDto dto = new ReviewVetRequestDto();
        dto.setIdVet(1);
        dto.setIdPetOwner(1);
        dto.setStars(BigDecimal.valueOf(5));
        dto.setComment("Excelente servicio");

        Vet vet = new Vet();
        vet.setId(1);

        PetOwner owner = new PetOwner();
        owner.setId(1);

        ReviewVet review = new ReviewVet();
        review.setIdReview(0);
        review.setVet(vet);
        review.setPetOwner(owner);
        review.setStars(dto.getStars());
        review.setComment(dto.getComment());

        // Configuramos el mock de petOwnerRepository
        when(petOwnerRepository.findById(dto.getIdPetOwner())).thenReturn(Optional.of(owner));
        // Configuramos el mock de vetRepository
        when(vetRepository.findById(dto.getIdVet())).thenReturn(Optional.of(vet));
        // Configuramos el mock de reviewVetRepository
        when(reviewVetRepository.save(review)).thenReturn(review);
        //Act
        ReviewVet result = reviewVetService.createReview(dto);

        //Assert
        Assert.notNull(result, "El resultado no debe ser nulo");
        assertEquals(result.getPetOwner(), owner);
        assertEquals(result.getVet(), vet);
        assertEquals(result.getStars(), dto.getStars());
        assertEquals(result.getComment(), dto.getComment());
    }

    @Test
    public void createReviewPetOwnerNotFound() {
        //Arrange
        ReviewVetRequestDto dto = new ReviewVetRequestDto();
        dto.setIdVet(1);
        dto.setIdPetOwner(1);
        dto.setStars(BigDecimal.valueOf(5));
        dto.setComment("Excelente servicio");

        Vet vet = new Vet();
        vet.setId(1);

        // Configuramos el mock de petOwnerRepository
        when(petOwnerRepository.findById(dto.getIdPetOwner())).thenReturn(Optional.empty());

        //Act
        try {
            reviewVetService.createReview(dto);
        } catch (Exception e) {
            //Assert
            assertEquals("Owner not found", e.getMessage());
        }
    }

    @Test
    public void createReviewVetNotFound() {
        //Arrange
        ReviewVetRequestDto dto = new ReviewVetRequestDto();
        dto.setIdVet(1);
        dto.setIdPetOwner(1);
        dto.setStars(BigDecimal.valueOf(5));
        dto.setComment("Excelente servicio");

        PetOwner owner = new PetOwner();
        owner.setId(1);

        // Configuramos el mock de petOwnerRepository
        when(petOwnerRepository.findById(dto.getIdPetOwner())).thenReturn(Optional.of(owner));
        // Configuramos el mock de vetRepository
        when(vetRepository.findById(dto.getIdVet())).thenReturn(Optional.empty());

        //Act
        try {
            reviewVetService.createReview(dto);
        } catch (Exception e) {
            //Assert
            assertEquals("Vet not found", e.getMessage());
        }
    }

    @Test
    public void getReviewsByVetId() {
        //Arrange
        int idVet = 1;
        //Act
        reviewVetService.getReviewsByVetId(idVet);
        //Assert
    }
}
