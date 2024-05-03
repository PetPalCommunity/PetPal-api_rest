package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDate(Date date);
    boolean existsByReason(String reason);
    boolean existsByIdAppointment(int idAppointment);
    Optional<Appointment> findByIdAppointment(int appointmentId);
}