package com.petpal.petpalservice.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.model.entity.Reminder;
import java.util.List;

import java.util.Optional;
import java.util.Set;
@Repository

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.petOwner.id =:ownerId")
    Optional<List<Pet>>  findByPetOwnerId(@Param("ownerId") int ownerId);

    @Query("SELECT p FROM Pet p WHERE p.petOwner.id =:ownerId AND p.id =:id")
    Optional<Pet> findByPetOwnerIdAndPetId(@Param("ownerId") int ownerId, @Param("id") int id);

    @Query("SELECT r FROM Reminder r WHERE r.pet.id =:petId")
    Optional<Set<Reminder>> findByPetId(@Param("petId") int petId);
}
