package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PersonalInfoDto;
import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;
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
  @PutMapping("/privacy")
  public ResponseEntity<Void> updatePrivacySettings(@RequestBody VisibilityRequestDto dto, @RequestParam String email) {
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // String email = authentication.getEmail(); // Obtener el correo electrónico del usuario autenticado
    service.updateVisibility(dto, email);
    return ResponseEntity.ok().build();
  }
  @GetMapping("/personalinfo")
  public ResponseEntity<PetOwnerResponseDto> updatePersonalInfo(@RequestBody PersonalInfoDto dto, @RequestParam String email) {
    PetOwnerResponseDto petOwnerResponseDto = service.updatePersonalInfo(dto, email);
    return ResponseEntity.ok(petOwnerResponseDto);
  }
}
