package com.petpal.petpalservice.controller;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.AppointmentResponseDto;
import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Appointment;
import com.petpal.petpalservice.service.AppointmentService;
import com.petpal.petpalservice.service.VetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService service;
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDto dto) {
        Appointment created = service.createAppointment(dto);
        return ResponseEntity.ok(created);
    }
    @PostMapping("/confirmAppointment")
    public ResponseEntity<Appointment> updateAppointmentConfirmation(@RequestBody AppointmentResponseDto dto) {
        Appointment updated = service.updateAppointmentConfirmation(dto);
        return ResponseEntity.ok(updated);
    }
}

