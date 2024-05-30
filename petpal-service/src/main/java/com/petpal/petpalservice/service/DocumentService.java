package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.NotFoundException;
import com.petpal.petpalservice.mapper.DocumentMapper;
import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.DocumentResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.model.entity.Pet;
import com.petpal.petpalservice.repository.DocumentRepository;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final MedicalRecordRepository medicalRecordRepository;

    public Document addDocument(DocumentRequestDto dto) {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findById(dto.getIdRecord());
        if (optionalRecord.isEmpty()) {
            throw new NotFoundException("Record not found");
        }
        Document document = new Document();
        document.setRecord(optionalRecord.get());
        document.setLink(dto.getLink());
        return documentRepository.save(document);
    }

    public List<DocumentResponseDto> getDocumentsByRecord(int id) {
        List<Document> documents = documentRepository.findByRecord(id);
        return documentMapper.convertToDtoList(documents);
    }
}
