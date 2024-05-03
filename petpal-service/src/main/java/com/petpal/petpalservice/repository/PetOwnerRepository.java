package com.petpal.petpalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petpal.petpalservice.model.entity.PetOwner;

import java.util.List;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {
    boolean existsByOwnerEmail(String ownerEmail);
    boolean existsByOwnerPhone(int ownerPhone);
    PetOwner findByOwnerEmail(String ownerEmail);
}
