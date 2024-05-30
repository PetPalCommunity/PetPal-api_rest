package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.InvalidDateFormatException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.DocumentMapper;
import com.petpal.petpalservice.mapper.MedicalRecordMapper;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDto;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.model.entity.Pet;
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
import java.util.Optional;

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
        try{
            Date sqlDate = Date.valueOf(date);
            List<MedicalRecord> records = medicalRecordRepository.findByDate(sqlDate);
            return medicalRecordMapper.converToDtoList(records);
        }
        catch (IllegalArgumentException e){
            throw new InvalidDateFormatException("Invalid date format");
        }
    }

    public MedicalRecord createMedicalRecord(MedicalRecordRequestDto dto) {
        Optional<Pet> optionalPet = petRepository.findById(dto.getIdPet());
        if (optionalPet.isEmpty()) {
            throw new NotFoundException("Pet not found");
        }

        Date originalDate = dto.getDate();
        LocalDate localDate = originalDate.toLocalDate();
        LocalDate nextDay = localDate.plusDays(1);
        Date nextDayDate = java.sql.Date.valueOf(nextDay);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPet(optionalPet.get());
        medicalRecord.setDate(nextDayDate);
        medicalRecord.setKind(dto.getKind());
        medicalRecord.setDescription(dto.getDescription());
        return medicalRecordRepository.save(medicalRecord);
    }
}
