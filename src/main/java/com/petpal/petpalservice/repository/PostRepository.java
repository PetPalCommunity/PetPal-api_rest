package com.petpal.petpalservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petpal.petpalservice.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

    @Query("SELECT p FROM Post p WHERE p.community.id = :communityId")
    List<Post> findByCommunityId(Long communityId);    

    @Query("SELECT p FROM Post p WHERE p.user.alias = :alias")
    List<Post> findByUserAlias(String alias);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.id = :postId")
    Optional<Post> findByUserIdAndPostId(Long userId, Long postId);
} 
