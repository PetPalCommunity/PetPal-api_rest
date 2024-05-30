package com.petpal.petpalservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.petpal.petpalservice.model.entity.ReviewVet;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewVetRepository extends JpaRepository<ReviewVet, Long> {
    boolean existsByIdReview(int idReview);
    Optional<ReviewVet> findByIdReview(int idReview);
    @Query("SELECT r FROM ReviewVet r WHERE r.vet.id = :idVet")
    List<ReviewVet> findByVetId(@Param("idVet") int idVet);
}