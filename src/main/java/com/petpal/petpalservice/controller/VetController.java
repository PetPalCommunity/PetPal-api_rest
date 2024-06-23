package com.petpal.petpalservice.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.petpal.petpalservice.model.dto.ReviewVetRequestDTO;
import com.petpal.petpalservice.model.dto.ReviewVetResponseDTO;
import com.petpal.petpalservice.model.dto.VetProfileDTO;
import com.petpal.petpalservice.model.dto.VetProfileUpdateDTO;
import com.petpal.petpalservice.model.dto.VisibilityRequestDTO;
import com.petpal.petpalservice.service.StorageService;
import com.petpal.petpalservice.service.VetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vets")
public class VetController {
    private final VetService service;
    private final StorageService storageService;

    @GetMapping("/search")
    public ResponseEntity<Page<VetProfileDTO>> searchVets(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String lastname,
                                                          @RequestParam(required = false) String location,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size){
        Page<VetProfileDTO> response = service.searchVets(name,lastname,location,page,size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/profile/{alias}")
    public ResponseEntity<VetProfileDTO> getProfile(@PathVariable String alias) {
        VetProfileDTO response = service.getProfile(alias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<VetProfileDTO> updatePersonalInfo(@Validated @RequestBody VetProfileUpdateDTO dto) {
        VetProfileDTO response = service.updatePersonalInfo(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    @PutMapping("/update-visibility")
    public ResponseEntity<VetProfileDTO> updateVisibility(@Validated @RequestBody VisibilityRequestDTO dto) {
        VetProfileDTO response = service.updateVisibility(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create-review")
    public ResponseEntity<ReviewVetResponseDTO> createReviewVet(@Validated @RequestBody ReviewVetRequestDTO dto) {
        ReviewVetResponseDTO response = service.createReviewVet(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{alias}")
    public ResponseEntity<List<ReviewVetResponseDTO>> getReviews(@PathVariable String alias) {
        List<ReviewVetResponseDTO> response = service.getReviews(alias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity<VetProfileDTO> upload(@RequestParam("file") MultipartFile file) {
        String path = storageService.store(file);
        VetProfileDTO response = service.updateProfilePicture(path);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
