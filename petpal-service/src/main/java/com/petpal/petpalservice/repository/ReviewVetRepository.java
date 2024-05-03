package com.petpal.petpalservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.petpal.petpalservice.model.entity.ReviewVet;

import java.util.Optional;


@Repository
public interface ReviewVetRepository extends JpaRepository<ReviewVet, Long> {
    boolean existsByIdReview(int idReview);
    Optional<ReviewVet> findByIdReview(int idReview);
}