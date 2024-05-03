package com.petpal.petpalservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petpal.petpalservice.model.entity.Pet;
import java.util.List;

@Repository

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.petOwner.id =:ownerId")
    List<Pet> findByPetOwnerId(@Param("ownerId") int ownerId);

    @Query("SELECT p FROM Pet p WHERE p.petOwner.id =:ownerId AND p.id =:id")
    Pet findByPetOwnerIdAndPetId(@Param("ownerId") int ownerId, @Param("id") int id);
}
