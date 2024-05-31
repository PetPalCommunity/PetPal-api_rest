package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PersonalInfoDto;
import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.dto.StorageService;
// import com.petpal.petpalservice.model.dto.UploadMediaResponse;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/petowners")
public class PetOwnerController {
  private final PetOwnerService service;
  private final StorageService storageService;
  public PetOwnerController(PetOwnerService service, StorageService storageService) {
    this.service = service;
    this.storageService = storageService;
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
  @PatchMapping("/personalinfo")
  public ResponseEntity<PetOwnerResponseDto> updatePersonalInfo(@RequestBody PersonalInfoDto dto, @RequestParam String email) {
    PetOwnerResponseDto petOwnerResponseDto = service.updatePersonalInfo(dto, email);
    return ResponseEntity.ok(petOwnerResponseDto);
  }

  @PostMapping("/upload")
  public ResponseEntity<PetOwnerResponseDto> upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("email") String email) {
    String path = storageService.store(multipartFile);
    PetOwnerResponseDto response = service.updateProfilePicture(email, path);
    return ResponseEntity.ok(response);
  }
  // return new UploadMediaResponse(path);

  @GetMapping("/{filename}")
  public ResponseEntity<Resource> getResource(@PathVariable String filename) throws IOException {
    Resource resource = storageService.loadAsResource(filename);
    String contentType = Files.probeContentType(resource.getFile().toPath());

    return ResponseEntity
    .ok()
    .header(HttpHeaders.CONTENT_TYPE, contentType)
    .body(resource);
  }
}
