package com.petpal.petpalservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetOwnerMapper;
import com.petpal.petpalservice.model.DTO.PetOwnerRequestDTO;
import com.petpal.petpalservice.model.DTO.PetOwnerResponseDTO;
import com.petpal.petpalservice.model.entities.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class PetOwnerService {
    private PetOwnerRepository petOwnerRepository;
    private final PetOwnerMapper communityMapper = new PetOwnerMapper();
    // cómo hacer q un petowner ingrese a una comunidad

}

