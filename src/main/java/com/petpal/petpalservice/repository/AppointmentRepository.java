package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.pet.id = :idPet")
    List<Appointment> findByPetId(Long idPet);

    @Query("SELECT a FROM Appointment a WHERE a.vet.id = :idVet")
    List<Appointment> findByVetId(Long idVet);

    @Query("SELECT a FROM Appointment a WHERE a.id = :idAppointment AND a.vet.id = :idVet")
    Optional<Appointment> findByIdAndVetId(Long idAppointment, Long idVet);

    @Query("SELECT a FROM Appointment a WHERE a.id = :idAppointment AND a.pet.id = :idPet")
    Optional<Appointment> findByIdAndPetId(Long idAppointment, Long idPet);
}