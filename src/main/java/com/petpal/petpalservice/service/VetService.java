package com.petpal.petpalservice.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.VetMapper;
import com.petpal.petpalservice.model.dto.ReviewVetRequestDTO;
import com.petpal.petpalservice.model.dto.ReviewVetResponseDTO;
import com.petpal.petpalservice.model.dto.VetProfileDTO;
import com.petpal.petpalservice.model.dto.VetProfileUpdateDTO;
import com.petpal.petpalservice.model.dto.VisibilityRequestDTO;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.ReviewVetRepository;
import com.petpal.petpalservice.repository.VetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VetService {
    private final VetRepository repository;
    private final ReviewVetRepository reviewVetRepository;

    private final VetMapper mapper;

    private final UserService userService;

    private final PetOwnerService petOwnerService;

    @Transactional(readOnly = true)
    public VetProfileDTO getProfile(String alias) {
        User user = userService.getUserByAuth();
        Vet vet = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (!alias.equals(user.getAlias()) && !vet.isProfileVisible()) {
            throw new IllegalArgumentException("El perfil no es visible para otros usuarios");
        }
        return mapper.convertToDTO(vet);
    }

    @Transactional
    public VetProfileDTO updateVisibility(VisibilityRequestDTO dto) {
        userService.updateVisibility(dto);
        Vet vet = getCurrentVet();
        return mapper.convertToDTO(vet);

    }

    @Transactional
    public VetProfileDTO updatePersonalInfo(VetProfileUpdateDTO dto) {
        Vet vet = getCurrentVet();
        System.out.println(vet.getSex());
        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if (value != null) {
                    Field perInfo;
                    try {
                        perInfo = vet.getClass().getDeclaredField(field.getName());
                    } catch (NoSuchFieldException e) {
                        perInfo = vet.getClass().getSuperclass().getDeclaredField(field.getName());
                    }
                    perInfo.setAccessible(true);
                    ReflectionUtils.setField(perInfo,vet,value);
                    perInfo.setAccessible(false);
                }
                field.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        repository.save(vet);
        return mapper.convertToDTO(vet);
    }

    @Transactional
    public VetProfileDTO updateProfilePicture(String path) {
        Vet vet = getCurrentVet();
        vet.setImage(path);
        repository.save(vet);
        return mapper.convertToDTO(vet);
    }

    @Transactional(readOnly = true)
    public Page<VetProfileDTO> searchVets(String vetName, String vetLastname, String vetLocation, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vet> vets = repository.findByVetNameAndVetLastnameAndVetLocation(vetName, vetLastname, vetLocation, pageable);
        return vets.map(mapper::convertToDTO);
    }

    @Transactional
    public ReviewVetResponseDTO createReviewVet(ReviewVetRequestDTO dto) {
        PetOwner owner = petOwnerService.getCurrentPetOwner();
        Vet vet = repository.findByAlias(dto.getAliasVet())
            .orElseThrow(() -> new ResourceNotFoundException("El veterinario no existe"));
        ReviewVet review = new ReviewVet();
        review.setVet(vet);
        review.setPetOwner(owner);
        review.setStars(BigDecimal.valueOf(dto.getStars()));
        review.setComment(dto.getComment());
        reviewVetRepository.save(review);
        vet.setReputation(calculateReputation(vet.getId()));
        return mapper.convertToReviewDTO(review);
    }
    
    @Transactional(readOnly = true)
    public List<ReviewVetResponseDTO> getReviews(String alias) {
        Vet vet = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El veterinario no existe"));
        List<ReviewVet> reviews = reviewVetRepository.findByVetId(vet.getId());
        return mapper.convertToReviewDTOList(reviews);
    }
   
   
    public Vet getCurrentVet() {
        User user = userService.getUserByAuth();
        if (!(user instanceof Vet)) {
            throw new ResourceNotFoundException("El usuario no es un Veterinario");
        }
        return (Vet) user;
    }

    private BigDecimal calculateReputation(Long vetId) {
        List<ReviewVet> reviews = reviewVetRepository.findByVetId(vetId);
        //Calculate reputation adding all the stars and dividing by the number of reviews
        return reviews.stream().map(ReviewVet::getStars).reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(reviews.size()));
    }


}
