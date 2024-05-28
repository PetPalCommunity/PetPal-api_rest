package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.service.VetService;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vets")
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

    @PostMapping("/signin")
    public ResponseEntity<Vet> signIn(@RequestBody SignInRequestDto dto) {
        Vet validated = service.validateSignIn(dto);
        return ResponseEntity.ok(validated);
    }
}
