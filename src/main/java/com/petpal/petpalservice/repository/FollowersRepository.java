package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import  java.util.List;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Integer> {
    
    @Query("SELECT COUNT(f)> 0 FROM Followers f WHERE f.follower.id = :followerId AND f.followed.id = :followedId") 
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Modifying
    @Query("DELETE FROM Followers f WHERE f.follower.id = :followerId AND f.followed.id = :followedId")
    void deleteByFollowerIdAndFollowedId(Long followerId ,Long followedId);

    @Query("SELECT f FROM Followers f WHERE f.followed.id = :followedId")
    List<Followers> findByFollowedId(Long followedId);

    @Query("SELECT f FROM Followers f WHERE f.follower.id = :followerId")
    List<Followers> findByFollowerId(Long followerId);

}