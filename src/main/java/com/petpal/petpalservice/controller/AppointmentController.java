package com.petpal.petpalservice.controller;
import com.petpal.petpalservice.model.dto.AppointmentRequestDTO;
import com.petpal.petpalservice.model.dto.AppointmentResponseDTO;
import com.petpal.petpalservice.model.dto.AppointmentUpdateDTO;
import com.petpal.petpalservice.model.dto.PaymentRequestDTO;
import com.petpal.petpalservice.service.AppointmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping("/petOwner/create")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Validated @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO created = service.createAppointment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/vet/confirm")
    public ResponseEntity<AppointmentResponseDTO> updateAppoinmentStatus(@Validated @RequestBody AppointmentUpdateDTO dto) {
        AppointmentResponseDTO updated = service.updateAppoinmentStatus(dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/vet/{alias}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByVet(@PathVariable String alias) {
        List<AppointmentResponseDTO> appointments = service.getAppointmentsByVet(alias);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/petOwner/{petName}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPet(@PathVariable String petName) {
        List<AppointmentResponseDTO> appointments = service.getAppointmentsByPet(petName);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @DeleteMapping("/petOwner/delete/{petName}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String petName,
                                                    @RequestParam Long id){
        service.deleteAppointment(petName, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/petOwner/pay/{petName}")
    public ResponseEntity<AppointmentResponseDTO> payAppointment(@Validated @RequestBody PaymentRequestDTO dto,
                                                                @PathVariable String petName) {
        AppointmentResponseDTO appointmentResponseDTO = service.payAppointment(petName,dto);
        return new ResponseEntity<>(appointmentResponseDTO, HttpStatus.OK);
    }

}