package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.dto.PaymentRequestDto;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Payment;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PaymentRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository paymentRepository, AppointmentRepository appointmentRepository){
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Payment createPayment(PaymentRequestDto paymentDto){
        Optional<Appointment> optionalAppointment = appointmentRepository.findByIdAppointment(paymentDto.getIdAppointment());
        if (optionalAppointment.isEmpty()) {
            throw new NotFoundException("Appointment not found");
        }
        Payment payment = new Payment();
        payment.setAppointment(optionalAppointment.get());
        payment.setAmount(paymentDto.getAmount());
        payment.setDate(paymentDto.getDate());
        payment.setDescription(paymentDto.getDescription());

        return paymentRepository.save(payment);
    }
}
