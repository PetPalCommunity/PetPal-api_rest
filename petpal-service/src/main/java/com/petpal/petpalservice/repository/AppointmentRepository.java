package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.pet.idPet = :idPet AND a.vet.idVet = :idVet")
    List<Appointment> findByPetIdAndVetId(@Param("idPet") Long idPet, @Param("idVet") Long idVet);
    Optional<Appointment> findByAppointmentId(Long appointmentId);

}