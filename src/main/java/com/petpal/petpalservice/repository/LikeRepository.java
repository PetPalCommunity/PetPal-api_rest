package com.petpal.petpalservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petpal.petpalservice.model.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long>{

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
} 