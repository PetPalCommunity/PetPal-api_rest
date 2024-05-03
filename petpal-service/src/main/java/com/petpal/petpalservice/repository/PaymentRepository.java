package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByIdPayment(int paymentId);
    Optional<Payment> findByIdPayment(Long paymentId);
}