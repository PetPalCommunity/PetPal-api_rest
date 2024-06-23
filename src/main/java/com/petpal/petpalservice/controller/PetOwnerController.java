package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.FollowerResponseDTO;
import com.petpal.petpalservice.model.dto.PetOwnerProfileDTO;
import com.petpal.petpalservice.model.dto.PetOwnerProfileUpdateDTO;
import com.petpal.petpalservice.model.dto.VisibilityRequestDTO;
import com.petpal.petpalservice.service.StorageService;

import lombok.RequiredArgsConstructor;
import com.petpal.petpalservice.service.PetOwnerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/petOwners")
public class PetOwnerController {
    private final PetOwnerService service;
    private final StorageService storageService;

    @GetMapping("/profile/{alias}")
    public ResponseEntity<PetOwnerProfileDTO> getProfile(@PathVariable String alias) {
        PetOwnerProfileDTO response = service.getProfile(alias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   @PatchMapping("/update")
    public ResponseEntity<PetOwnerProfileDTO> updatePersonalInfo(@Validated @RequestBody PetOwnerProfileUpdateDTO dto) {
        PetOwnerProfileDTO response = service.updatePersonalInfo(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    @PostMapping("/upload")
    public ResponseEntity<PetOwnerProfileDTO> upload(@RequestParam("file") MultipartFile file) {
        String path = storageService.store(file);
        PetOwnerProfileDTO response = service.updateProfilePicture(path);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-visibility")
    public ResponseEntity<PetOwnerProfileDTO> updateVisibility(@Validated @RequestBody VisibilityRequestDTO dto) {
        PetOwnerProfileDTO response = service.updateVisibility(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestParam String alias) {
        service.follow(alias);
        return new ResponseEntity<>("Has seguido a un nuevo usuario", HttpStatus.OK);
   }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestParam String alias) {
        service.unfollow(alias);
        return new ResponseEntity<>("Has dejado de seguir a un usuario", HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowerResponseDTO>> getFollowers(@RequestParam String alias) {
        List<FollowerResponseDTO> response = service.getFollowers(alias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/followed")
    public ResponseEntity<List<FollowerResponseDTO>> getFollowed(@RequestParam String alias) {
        List<FollowerResponseDTO> response = service.getFollowed(alias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}