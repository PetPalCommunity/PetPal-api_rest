package com.petpal.petpalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petpal.petpalservice.model.entity.Pet;
import java.util.List;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.owner.alias =:alias")
    List<Pet>  findByPetOwnerAlias(String alias);

    @Query("SELECT p FROM Pet p WHERE p.owner.alias =:alias AND p.name =:name")
    Optional<Pet> findByPetOwnerAliasAndPetName(String alias, String name);

    @Query("SELECT COUNT(p) > 0 FROM Pet p WHERE p.owner.alias =:alias AND p.name =:name")
    boolean existsByPetOwnerAliasAndName(String alias, String name);




}