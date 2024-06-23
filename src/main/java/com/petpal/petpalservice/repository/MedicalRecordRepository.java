package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPetId(Long petId);

    @Query("SELECT m FROM MedicalRecord m WHERE m.pet.id = :petId AND m.id = :recordId")
    Optional<MedicalRecord> findByPetIdAndRecordId(Long petId, Long recordId);

    @Query("SELECT m FROM MedicalRecord m WHERE m.pet.id = :petId AND m.date = :date")
    List<MedicalRecord> findByPetNameAndRecordId(Long petId, Date date);
}
