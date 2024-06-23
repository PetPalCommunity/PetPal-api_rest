package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.id = :paymentId")
    Optional<Payment> findByIdPayment(Long paymentId);

    @Query("SELECT p FROM Payment p WHERE p.appointment.id = :appointmentId")
    Optional<Payment> findByAppointmentId(Long appointmentId);
}