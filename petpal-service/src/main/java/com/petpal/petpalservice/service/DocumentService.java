package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.DocumentMapper;
import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.DocumentResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.repository.DocumentRepository;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final MedicalRecordRepository medicalRecordRepository;

    public Document addDocument(DocumentRequestDto dto) {
        if(!medicalRecordRepository.existsById(dto.getIdRecord())){
            throw new NotFoundException("Registro no encontrado");
        }
        Document document = documentMapper.convertToEntity(dto);
        return documentRepository.save(document);
    }

    public List<DocumentResponseDto> getDocumentsByRecord(int id) {
        List<Document> documents = documentRepository.findByRecord(id);
        return documentMapper.convertToDtoList(documents);
    }
}
