package com.petpal.petpalservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petpal.petpalservice.model.entity.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long>{
    
    Optional<PetOwner> findByAlias(String alias);
}
