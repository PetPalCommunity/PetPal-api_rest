package com.petpal.petpalservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petpal.petpalservice.model.entity.PetOwner;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {
    boolean existsByOwnerEmail(String ownerEmail);
    boolean existsByOwnerPhone(int ownerPhone);
    Optional<PetOwner> findByOwnerEmail(String ownerEmail);
}
