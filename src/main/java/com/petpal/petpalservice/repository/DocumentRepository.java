package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.record.id = :recordId")
    List<Document> findByRecord(Long recordId);
}
