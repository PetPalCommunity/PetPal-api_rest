package com.petpal.petpalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.petpal.petpalservice.model.entity.Vet;

import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
    boolean existsById(int id);
    boolean existsByVetEmail(String vetEmail);
    boolean existsByVetPhone(int vetPhone);
    boolean existsByVetLicenseNumber(int vetLicenseNumber);
    Vet findByVetEmail(String vetEmail);
    Optional<Vet> findById(int id);
}
