package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.service.PetService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/api/pets")
@AllArgsConstructor
public class PetController {

    private final PetService service;

    @PostMapping("/create")
    public ResponseEntity<PetResponseDto> createPet(@Validated @RequestBody PetRequestDto dto) {
        PetResponseDto createdPet = service.createPet(dto);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<PetResponseDto>> getPetsByOwnerId(@PathVariable int ownerId) {
        List<PetResponseDto> pets = service.getPetsByOwnerId(ownerId);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
    
    //http://localhost:8080/api/v1/accounts/4
    @GetMapping("/{ownerId}/{id}")
    public ResponseEntity<PetResponseDto> getPetById(@PathVariable int ownerId,@PathVariable int id) {
        PetResponseDto pet = service.getPetById(ownerId,id);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        service.deletePet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
