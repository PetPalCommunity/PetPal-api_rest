package com.petpal.petpalservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.petpal.petpalservice.model.entities.CommunityUser;

public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long>{
}

