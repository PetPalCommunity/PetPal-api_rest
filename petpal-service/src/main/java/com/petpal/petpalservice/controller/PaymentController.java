package com.petpal.petpalservice.controller;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.dto.PaymentRequestDto;
import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.model.entity.Payment;
import com.petpal.petpalservice.service.AppointmentService;
import com.petpal.petpalservice.service.PaymentService;
import com.petpal.petpalservice.service.VetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequestDto dto) {
        Payment created = service.createPayment(dto);
        return ResponseEntity.ok(created);
    }
}