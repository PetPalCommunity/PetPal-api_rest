package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordResponseDto;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicalRecordMapper {
    private final ModelMapper modelMapper;
    public MedicalRecord convertToEntity(MedicalRecordRequestDto medicalRecordDTO) {
        return modelMapper.map(medicalRecordDTO, MedicalRecord.class);
    }

    public MedicalRecordResponseDto convertToDto(MedicalRecord medicalRecord) {
        return modelMapper.map(medicalRecord, MedicalRecordResponseDto.class);
    }


    public List<MedicalRecordResponseDto> converToDtoList(List<MedicalRecord> records){
        return records.stream()
                .map(this::convertToDto)
                .toList();
    }
}
