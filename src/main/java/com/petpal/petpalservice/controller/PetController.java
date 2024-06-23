package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.DocumentResponseDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDTO;
import com.petpal.petpalservice.model.dto.NewPetRequestDTO;
import com.petpal.petpalservice.model.dto.PetResponseDTO;
import com.petpal.petpalservice.model.dto.PetUpdateRequestDTO;
import com.petpal.petpalservice.model.dto.ReminderResponseDTO;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDTO;
import com.petpal.petpalservice.model.dto.NewReminderRequestDTO;
import com.petpal.petpalservice.service.PetService;
import com.petpal.petpalservice.service.StorageService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService service;
    private final StorageService storageService;
    
    @PostMapping("/create")
    public ResponseEntity<PetResponseDTO> createPet(@Validated @RequestBody NewPetRequestDTO dto) {
        PetResponseDTO createdPet = service.createPet(dto);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }
    
    @GetMapping("/{ownerAlias}")
    public ResponseEntity<List<PetResponseDTO>> getPetsByOwner(@PathVariable String ownerAlias) {
        List<PetResponseDTO> pets = service.getPetsByOwner(ownerAlias);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
    
    @GetMapping("/{ownerAlias}/{petName}")
    public ResponseEntity<PetResponseDTO> getPetByName(@PathVariable String ownerAlias,@PathVariable String petName) {
        PetResponseDTO pet = service.getPetByName(ownerAlias,petName);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
    
    @PatchMapping("/{petName}")
    public ResponseEntity<PetResponseDTO> updateAccount(@RequestBody PetUpdateRequestDTO petDTO,
                                                         @PathVariable String petName) {
        PetResponseDTO updatedPet = service.updatePet(petName,petDTO);
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }
    
    @DeleteMapping("/{petName}")
    public ResponseEntity<Void> deletePet(@PathVariable String petName) {
        service.deletePet(petName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{petName}/upload")
    public ResponseEntity<PetResponseDTO> upload(@RequestParam("file") MultipartFile file,
                                                     @PathVariable String petName) {
        String path = storageService.store(file);
        PetResponseDTO response = service.updateProfilePicture(petName,path);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Reminders
    @PostMapping("/reminders")
    public ResponseEntity<ReminderResponseDTO> createReminder(@Validated @RequestBody NewReminderRequestDTO dto) {
        ReminderResponseDTO createdReminder = service.createReminder(dto);
        return new ResponseEntity<>(createdReminder, HttpStatus.CREATED);
    }

    @GetMapping("/reminders/{petName}/all")
    public ResponseEntity<List<ReminderResponseDTO>> getRemindersByPetName(@PathVariable String petName) {
        List<ReminderResponseDTO> reminders = service.getRemindersByPetName(petName);
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @GetMapping("/reminders/{petName}")
    public ResponseEntity<ReminderResponseDTO> getReminderByPetName(@PathVariable String petName,
                                                                    @RequestParam Long id) {
        ReminderResponseDTO reminder = service.getReminderByPetName(petName,id);
        return new ResponseEntity<>(reminder, HttpStatus.OK);
    }

    @DeleteMapping("/reminders/{petName}")
    public ResponseEntity<Void> deleteReminder(@PathVariable String petName,
                                                @RequestParam Long id) {
        service.deleteReminder(petName,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/reminders/{petName}")
    public ResponseEntity<ReminderResponseDTO> updateReminder(@PathVariable String petName,
                                                            @Validated @RequestBody ReminderUpdateRequestDTO reminderDTO) {
        ReminderResponseDTO updatedReminder = service.updateReminder(petName,reminderDTO);
        return new ResponseEntity<>(updatedReminder, HttpStatus.OK);
    }

    @PostMapping("/reminders/sendEmail")
    public ResponseEntity<String> sendEmailReminder(@RequestParam int idPet,
                                                @RequestParam String date,
                                                @RequestParam String time) {
        service.sendReminder(idPet,date,time);
        return new ResponseEntity<>("Correo enviado", HttpStatus.OK);
    }
    

    //Medical Records
    @PostMapping("/medicalRecords")
    public ResponseEntity<MedicalRecordResponseDTO> createMedicalRecord(@Validated @RequestBody MedicalRecordRequestDTO dto) {
        MedicalRecordResponseDTO createdMedicalRecord = service.createMedicalRecord(dto);
        return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
    }

    @GetMapping("/medicalRecords/{petName}/all")
    public ResponseEntity<List<MedicalRecordResponseDTO>> getMedicalRecordsByPetName(@PathVariable String petName) {
        List<MedicalRecordResponseDTO> medicalRecords = service.getMedicalRecordsByPetName(petName);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/medicalRecords/{petName}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecordByPetName(@PathVariable String petName,
                                                                    @RequestParam Long id) {
        MedicalRecordResponseDTO medicalRecord = service.getMedicalRecordByPetName(petName,id);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @DeleteMapping("/medicalRecords/{petName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String petName,
                                                @RequestParam Long id) {
        service.deleteMedicalRecord(petName,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/medicalRecords/documents/{petName}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByMedicalRecord(@PathVariable String petName,
                                                                    @RequestParam Long id) {
        List<DocumentResponseDTO> documents = service.getDocumentsByMedicalRecord(petName,id);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PostMapping("/medicalRecords/{petName}/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                     @PathVariable String petName,
                                                     @RequestParam Long id) {
        String path = storageService.store(file);
        service.addDocumentToMedicalRecord(petName,id,path);
        return new ResponseEntity<>("Documento subido", HttpStatus.OK);
    }

}
