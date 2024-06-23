package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.DocumentResponseDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDTO;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDTO;
import com.petpal.petpalservice.model.dto.NewPetRequestDTO;
import com.petpal.petpalservice.model.dto.NewReminderRequestDTO;
import com.petpal.petpalservice.model.dto.PetResponseDTO;
import com.petpal.petpalservice.model.dto.ReminderResponseDTO;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.model.entity.Pet;
import lombok.AllArgsConstructor;
import com.petpal.petpalservice.model.entity.Reminder;

import java.util.List;

@AllArgsConstructor
@Component
public class PetMapper {
    private final ModelMapper modelMapper;

    public Pet convertToEntityPet(NewPetRequestDTO dto) {
        return modelMapper.map(dto, Pet.class);
    }

    public PetResponseDTO convertToDtoPet(Pet pet) {
        return modelMapper.map(pet, PetResponseDTO.class);
    }

    public List<PetResponseDTO> convertToListDtoPet(List<Pet> pets) {
        return pets.stream()
        .map(this::convertToDtoPet)
        .toList();
    }

    public Reminder convertToEntityReminder(NewReminderRequestDTO dto) {
        return modelMapper.map(dto, Reminder.class);
    }

    public ReminderResponseDTO convertToDtoReminder(Reminder reminder) {
        return modelMapper.map(reminder, ReminderResponseDTO.class);
    }

    public List<ReminderResponseDTO> convertToListDtoReminder(List<Reminder> reminders) {
        return reminders.stream()
        .map(this::convertToDtoReminder)
        .toList();
    }

    public MedicalRecord convertToEntityMedicalRecord(MedicalRecordRequestDTO dto) {
        return modelMapper.map(dto, MedicalRecord.class);
    }

    public MedicalRecordResponseDTO convertToDtoMedicalRecord(MedicalRecord medicalRecord) {
        return modelMapper.map(medicalRecord, MedicalRecordResponseDTO.class);
    }

    public List<MedicalRecordResponseDTO> convertToListDtoMedicalRecord(List<MedicalRecord> medicalRecords) {
        return medicalRecords.stream()
        .map(this::convertToDtoMedicalRecord)
        .toList();
    }

    public DocumentResponseDTO convertToDtoDocument(Document document) {
        return modelMapper.map(document, DocumentResponseDTO.class);
    }

    public List<DocumentResponseDTO> convertToListDtoDocument(List<Document> documents) {
        return documents.stream()
        .map(this::convertToDtoDocument)
        .toList();
    }
}
