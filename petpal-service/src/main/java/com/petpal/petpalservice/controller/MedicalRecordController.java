package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.service.DocumentService;
import com.petpal.petpalservice.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class MedicalRecordController{
    private final MedicalRecordService service;

    @PostMapping("/addRecord")
    public ResponseEntity<MedicalRecord> addRecord(@RequestBody MedicalRecordRequestDto dto){
        MedicalRecord created = service.createMedicalRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/viewRecords")
    public ResponseEntity<List<MedicalRecordResponseDto>> getRecordsbyDate(@RequestParam String date){
        List<MedicalRecordResponseDto> records = service.getMedicalRecordsByDate(date);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/viewAllRecords")
    public ResponseEntity<List<MedicalRecordResponseDto>> getRecords(){
        List<MedicalRecordResponseDto> records = service.getMedicalRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}