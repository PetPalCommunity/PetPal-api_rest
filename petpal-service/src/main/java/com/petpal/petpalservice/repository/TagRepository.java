package com.petpal.petpalservice.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petpal.petpalservice.model.entities.Community;

public interface TagRepository extends JpaRepository<Community, Long>{
    Optional <Community> findTagByName(String name);
}

