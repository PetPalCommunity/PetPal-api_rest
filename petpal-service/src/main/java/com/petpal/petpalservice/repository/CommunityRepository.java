package com.petpal.petpalservice.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petpal.petpalservice.model.entities.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>{
    Optional <Community> findCommunityByName(String name);
}
