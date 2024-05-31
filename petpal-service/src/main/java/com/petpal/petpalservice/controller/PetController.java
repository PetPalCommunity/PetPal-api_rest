package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.dto.petUpdateRequestDto;
import com.petpal.petpalservice.model.dto.ReminderResponseDto;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDto;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;

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

    @PatchMapping("/{ownerId}/{id}")
    public ResponseEntity<PetResponseDto> updateAccount(@PathVariable int ownerId,@PathVariable int id,
                                                            @Validated @RequestBody petUpdateRequestDto petDTO) {
        PetResponseDto updatedPet = service.updatePet(ownerId,id, petDTO);
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }
    
    @DeleteMapping("/{ownerId}/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int ownerId,@PathVariable int id) {
        service.deletePet(ownerId,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reminders")
    public ResponseEntity<ReminderResponseDto> createReminder(@Validated @RequestBody ReminderRequestDto dto) {
        ReminderResponseDto createdReminder = service.createReminder(dto);
        return new ResponseEntity<>(createdReminder, HttpStatus.CREATED);
    }

    @GetMapping("/reminders/{petId}")
    public ResponseEntity<List<ReminderResponseDto>> getRemindersByPetId(@PathVariable int petId) {
        List<ReminderResponseDto> reminders = service.getRemindersByPetId(petId);
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @GetMapping("/reminders/{petId}/{id}")
    public ResponseEntity<ReminderResponseDto> getReminderByPetId(@PathVariable int petId,@PathVariable int id) {
        ReminderResponseDto reminder = service.getReminderByPetId(petId,id);
        return new ResponseEntity<>(reminder, HttpStatus.OK);
    }

    @DeleteMapping("/reminders/{petId}/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable int petId,@PathVariable int id) {
        service.deleteReminder(petId,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/reminders/{petId}/{id}")
    public ResponseEntity<ReminderResponseDto> updateReminder(@PathVariable int petId,@PathVariable int id,
                                                            @Validated @RequestBody ReminderUpdateRequestDto reminderDTO) {
        ReminderResponseDto updatedReminder = service.updateReminder(petId,id, reminderDTO);
        return new ResponseEntity<>(updatedReminder, HttpStatus.OK);
    }

    @PostMapping("/reminders/sendEmail")
    public ResponseEntity<String> sendEmailReminder(@RequestParam int idPet,
                                                @RequestParam String date,
                                                @RequestParam String time) {
        service.sendReminder(idPet,date,time);
        return new ResponseEntity<>("Correo enviado", HttpStatus.OK);
    }
    

}
