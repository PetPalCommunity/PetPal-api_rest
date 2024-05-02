package com.petpal.petpalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.petpal.petpalservice.model.entity.Vet;
import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
    boolean existsByVetEmail(String vetEmail);
    boolean existsByVetPhone(int vetPhone);
    boolean existsByVetLicenseNumber(int vetLicenseNumber);
    Vet findByVetEmail(String vetEmail);


    @Query("SELECT v FROM Vet v WHERE " +
            "    (:vetName is null or SUBSTRING(v.vetName, 1, 2) = :vetName or "+" v.vetName = :vetName) "
            +
            "and (:vetLastname is null or " + "SUBSTRING(v.vetLastname, 1, 2) = :vetLastname or "+" v.vetLastname = :vetLastname) "
            +
            "and (:vetLocation is null or v.vetLocation = :vetLocation)")
    List<Vet> findByVetNameAndVetLastnameAndVetLocation(String vetName, String vetLastname, String vetLocation);
}

