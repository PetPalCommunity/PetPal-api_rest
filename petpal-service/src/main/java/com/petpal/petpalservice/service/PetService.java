package com.petpal.petpalservice.service;

import org.springframework.stereotype.Service;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetMapper;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;



import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetOwnerRepository PetOwnerRepository;
    private final PetMapper mapper;

    @Transactional
    public PetResponseDto createPet(PetRequestDto dto) {
        PetOwner owner = PetOwnerRepository.findById(dto.getOwnerId());
        Pet pet = mapper.dtoToEntity(dto);
        pet.setPetOwner(owner);
        petRepository.save(pet);
        return mapper.entityToDto(pet);
    }

    @Transactional(readOnly = true)
    public PetResponseDto getPetById(int ownerId, int id) {
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id);
               /*  .orElseThrow(()-> new ResourceNotFoundException("Mascota no encontrada:"+id+" del dueño:"+ownerId)); */
        return mapper.entityToDto(pet);
    }

    @Transactional
    public void deletePet(int petId) {
        petRepository.deleteById(petId);
    }

    @Transactional(readOnly =true)
    public List<PetResponseDto> getPetsByOwnerId(int ownerId) {
        List<Pet> pets = petRepository.findByPetOwnerId(ownerId);
        return mapper.entityToListDto(pets);
    }
}
