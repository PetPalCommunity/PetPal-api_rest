package com.petpal.petpalservice.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petpal.petpalservice.model.entity.Vet;

public interface VetRepository extends JpaRepository<Vet, Long>{
    
   Optional<Vet> findByAlias(String alias);

   @Query("SELECT v FROM Vet v " +
            "WHERE (:firstName IS NULL OR v.firstName LIKE %:firstName%) " +
            "AND (:lastName IS NULL OR v.lastName LIKE %:lastName%)" +
            "AND (:location IS NULL OR v.location LIKE %:location%) " +
            "ORDER BY v.reputation DESC")
   Page<Vet> findByVetNameAndVetLastnameAndVetLocation(String firstName, String lastName, String location, Pageable pageable);
}
