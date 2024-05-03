package com.petpal.petpalservice.service;

import org.springframework.stereotype.Service;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetMapper;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.dto.petUpdateRequestDto;
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
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                 .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no le pertenece al dueño co el id "+ownerId)); 
        return mapper.entityToDto(pet);
    }

    @Transactional
    public void deletePet(int ownerId, int id) {
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no puede ser eliminada por dueño con el id "+ownerId)); 
        petRepository.delete(pet);
    }

    @Transactional
    public PetResponseDto updatePet(int ownerId, int id, petUpdateRequestDto dto){
        Pet pet = petRepository.findByPetOwnerIdAndPetId(ownerId,id)
                    .orElseThrow(()-> new ResourceNotFoundException("La mascota con el id "+id+" no puede ser editada por el dueño con el id "+ownerId));        
        if (dto.getPetName() != null) {
            pet.setPetName(dto.getPetName());
        }
        if (dto.getPetSpecies() != null) {
            pet.setPetSpecies(dto.getPetSpecies());
        }
        if (dto.getPetBreed() != null) {
            pet.setPetBreed(dto.getPetBreed());
        }
        if (dto.getPetAge() != 0) {
            pet.setPetAge(dto.getPetAge());
        }
        if (dto.getPetSex() != null) {
            pet.setPetSex(dto.getPetSex());
        }
        if (dto.getImage() != null) {
            pet.setImage(dto.getImage());
        }

        pet = petRepository.save(pet);

        return  mapper.entityToDto(pet);
    }

    @Transactional(readOnly =true)
    public List<PetResponseDto> getPetsByOwnerId(int ownerId) {
        List<Pet> pets = petRepository.findByPetOwnerId(ownerId)
                    .orElseThrow(()-> new ResourceNotFoundException("El dueño "+ownerId+" no tiene mascotas")); 
        return mapper.entityToListDto(pets);
    }
}
