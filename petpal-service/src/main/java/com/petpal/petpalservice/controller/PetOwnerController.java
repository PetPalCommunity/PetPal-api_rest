package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/petowners")
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
  @PatchMapping("/privacy")
  public ResponseEntity<VisibilityRequestDto> updatePrivacySettings(@RequestBody VisibilityRequestDto dto, @RequestParam String email) {
    VisibilityRequestDto response = service.updateVisibility(dto, email);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
