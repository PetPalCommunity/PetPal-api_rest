package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    boolean existsByIdPet(int idPet);
    Optional<Pet> findByIdPet(int idPet);

}