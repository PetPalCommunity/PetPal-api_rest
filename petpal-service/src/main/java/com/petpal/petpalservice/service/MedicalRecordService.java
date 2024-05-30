package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.InvalidDateFormatException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.DocumentMapper;
import com.petpal.petpalservice.mapper.MedicalRecordMapper;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDto;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.repository.DocumentRepository;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import com.petpal.petpalservice.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService{
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final PetRepository petRepository;

    public List<MedicalRecordResponseDto> getMedicalRecords() {
        List<MedicalRecord> records = medicalRecordRepository.findAll();
        return medicalRecordMapper.converToDtoList(records);
    }

    public List<MedicalRecordResponseDto> getMedicalRecordsByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Formato de fecha incorrecto. Porfavor usar 'yyyy-MM-dd'.");
        }
        Date sqlDate = java.sql.Date.valueOf(date);
        List<MedicalRecord> records = medicalRecordRepository.findByDate(sqlDate);
        return medicalRecordMapper.converToDtoList(records);
    }

    public MedicalRecord createMedicalRecord(MedicalRecordRequestDto medicalRecordDTO) {
        if(!petRepository.existsByIdPet(medicalRecordDTO.getIdPet())) {
            throw new NotFoundException("Mascota no existe");
        }
        MedicalRecord medicalRecord = medicalRecordMapper.convertToEntity(medicalRecordDTO);
        return medicalRecordRepository.save(medicalRecord);
    }
}
