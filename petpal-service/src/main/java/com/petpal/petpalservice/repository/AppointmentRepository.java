package com.petpal.petpalservice.repository;

import com.fasterxml.jackson.datatype.jdk8.OptionalSerializer;
import com.petpal.petpalservice.model.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDate(Date date);
    boolean existsByReason(String reason);
    boolean existsByIdAppointment(int idAppointment);
    Optional<Appointment> findByIdAppointment(int appointmentId);
    @Query("SELECT a FROM Appointment a WHERE a.pet.idPet = :idPet")
    List<Appointment> findByPetId(@Param("idPet") int idPet);
}