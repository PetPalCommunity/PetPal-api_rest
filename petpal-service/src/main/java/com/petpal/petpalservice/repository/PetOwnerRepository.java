package com.petpal.petpalservice.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petpal.petpalservice.model.entities.Community;

public interface PetOwnerRepository extends JpaRepository<Community, Long>{
    Optional <Community> findPetOwnerByUserName(String userName);
}

