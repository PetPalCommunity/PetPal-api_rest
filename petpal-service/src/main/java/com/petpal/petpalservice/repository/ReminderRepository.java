package com.petpal.petpalservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petpal.petpalservice.model.entity.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer>{
    @Query("SELECT r FROM Reminder r WHERE r.pet.id =:petId AND r.id =:id")
    Optional<Reminder> findByPetIdAndReminderId(@Param("petId") int petId, @Param("id") int id);
}
