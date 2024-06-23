package com.petpal.petpalservice.repository;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petpal.petpalservice.model.entity.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer>{
    @Query("SELECT r FROM Reminder r WHERE r.pet.id =:petId AND r.id =:id")
    Optional<Reminder> findByPetIdAndReminderId(Long petId, Long id);

    @Query("SELECT r FROM Reminder r WHERE r.pet.name =:petName AND r.id =:id")
    Optional<Reminder> findByPetNameAndReminderId(String petName, Long id);

    List<Reminder> findByPetId(Long petId);

    @Query("SELECT COUNT(r) > 0 FROM Reminder r WHERE r.pet.id =:petId AND r.name =:name")
    boolean existsByPetIdAndReminderName(Long petId, String name);

}
