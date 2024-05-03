package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.RecoverPasswordDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/petowners")
public class PetOwnerController {
    private final PetOwnerService service;

    public PetOwnerController(PetOwnerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<PetOwner> registerPetOwner(@RequestBody PetOwnerRequestDto dto) {
        PetOwner created = service.createPetOwner(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/signin")
    public ResponseEntity<PetOwner> signIn(@RequestBody SignInRequestDto dto) {
        PetOwner validated = service.validateSignIn(dto);
        return ResponseEntity.ok(validated);
    }

    @PostMapping("/recoverPassword")
    public ResponseEntity<Void> recoverPetOwnerPassword(@RequestBody RecoverPasswordDto dto) {
        service.recoverPetOwnerPassword(dto);
        return ResponseEntity.ok().build();
    }
}