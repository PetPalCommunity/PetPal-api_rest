package com.petpal.petpalservice.controller;




import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/petowners")
public class PetOwnerController {
    private final PetOwnerService service;

    public PetOwnerController(PetOwnerService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPetOwner(@RequestBody PetOwnerRequestDto dto) {
        try {
            PetOwner created = service.createPetOwner(dto);
            return ResponseEntity.ok(created);
        } catch (MissingRequiredFieldException | InvalidEmailFormatException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateResourceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
