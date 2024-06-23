package com.petpal.petpalservice.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petpal.petpalservice.model.entity.Community;
import com.petpal.petpalservice.model.entity.User;

public interface CommunityRepository extends JpaRepository<Community, Long>{
    Optional <Community> findCommunityByName(String name);
    Optional <Community> findById(Long id);

    boolean existsByName(String name);

    @Query("SELECT COUNT(c) > 0 FROM Community c JOIN c.users u WHERE u.id = :userId AND c.id = :communityId")
    boolean existByUserIdAndCommunityId(@Param("userId") Long userId, @Param("communityId") Long communityId);

    @Query("SELECT c FROM Community c JOIN c.users u WHERE u.id = :userId")
    List<Community> findCommunitiesByUserId(Long userId);

    @Query("SELECT u FROM User u JOIN u.communities c WHERE c.name = :name")
    List<User> findUsersByCommunityName(String name);

    @Modifying
    @Query(value = "DELETE FROM user_community WHERE user_id = :userId AND community_id = :communityId", nativeQuery = true)
    void deleteByUserIdAndCommunityId(@Param("userId") Long userId, @Param("communityId") Long communityId);

    @Query("SELECT c FROM Community c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:tag IS NULL OR c.tags LIKE %:tag%) " +
            "ORDER BY c.countMembers DESC")
   Page<Community> findCommunitiesByNameContaining(String name, String tag, Pageable pageable);
    
}
