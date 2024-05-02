package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.service.VetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vets")
public class VetController {
    private final VetService service;

    public VetController(VetService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Vet> registerVet(@RequestBody VetRequestDto dto) {
        Vet created = service.createVet(dto);
        return ResponseEntity.ok(created);
    }
}
