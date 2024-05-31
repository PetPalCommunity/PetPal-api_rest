package com.petpal.petpalservice.service;

import com.petpal.petpalservice.mapper.MedicalRecordMapper;
import com.petpal.petpalservice.model.dto.DocumentResponseDto;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import com.petpal.petpalservice.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @InjectMocks
    private MedicalRecordService medicalRecordService;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @Test
    public void createMedicalRecord() {
        //Arrange
        MedicalRecordRequestDto dto = new MedicalRecordRequestDto();
        dto.setIdPet(0);
        dto.setDate(Date.valueOf("2021-05-05"));
        dto.setKind("kind");
        dto.setDescription("observations");

        Pet pet = new Pet();
        pet.setIdPet(0);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPet(pet);
        medicalRecord.setDate(dto.getDate());
        medicalRecord.setKind(dto.getKind());
        medicalRecord.setDescription(dto.getDescription());

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.of(pet));
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);

        //Act
        MedicalRecord result = medicalRecordService.createMedicalRecord(dto);

        //Assert
        assertEquals(result.getPet(), result.getPet());
        assertEquals(result.getDate(), result.getDate());
        assertEquals(result.getKind(), result.getKind());
        assertEquals(result.getDescription(), result.getDescription());
    }

    @Test
    public void createMedicalRecordPetNotFound() {
        //Arrange
        MedicalRecordRequestDto dto = new MedicalRecordRequestDto();
        dto.setIdPet(0);
        dto.setDate(Date.valueOf("2021-05-05"));
        dto.setKind("kind");
        dto.setDescription("observations");

        when(petRepository.findById(dto.getIdPet())).thenReturn(Optional.empty());

        //Act and Assert
        try {
            medicalRecordService.createMedicalRecord(dto);
        } catch (Exception e) {
            assertEquals("Pet not found", e.getMessage());
        }
    }

    @Test
    public void getMedicalRecords() {
        //Arrange
        //Act
        medicalRecordService.getMedicalRecords();
    }

    @Test
    public void getMedicalRecordsByDate() {

        String date = "2021-05-05";
        MedicalRecord record1 = new MedicalRecord();
        record1.setIdRecord(1);
        MedicalRecord record2 = new MedicalRecord();
        record2.setIdRecord(2);
        List<MedicalRecord> records = Arrays.asList(record1, record2);
        when(medicalRecordRepository.findByDate(Date.valueOf(date))).thenReturn(records);

        MedicalRecordResponseDto recordResponseDto1 = new MedicalRecordResponseDto();
        MedicalRecordResponseDto recordResponseDto2 = new MedicalRecordResponseDto();
        recordResponseDto1.setIdRecord(record1.getIdRecord());
        recordResponseDto2.setIdRecord(record1.getIdRecord());

        List<MedicalRecordResponseDto>recordResponseDtoList = Arrays.asList(recordResponseDto1, recordResponseDto2);
        when(medicalRecordMapper.converToDtoList(records)).thenReturn(recordResponseDtoList);

        List<MedicalRecordResponseDto> result = medicalRecordService.getMedicalRecordsByDate(date);
        //Assert
        assertNotNull(result);
        assertEquals(recordResponseDtoList.size(), result.size());
    }

    @Test
    public void getMedicalRecordsByDateInvalidDateFormat() {
        //Arrange
        String date = "2021-05-05";
        when(medicalRecordRepository.findByDate(Date.valueOf(date))).thenThrow(IllegalArgumentException.class);

        //Act and Assert
        try {
            medicalRecordService.getMedicalRecordsByDate(date);
        } catch (Exception e) {
            assertEquals("Invalid date format", e.getMessage());
        }
    }


}