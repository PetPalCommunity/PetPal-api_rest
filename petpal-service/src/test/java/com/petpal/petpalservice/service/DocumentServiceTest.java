package com.petpal.petpalservice.service;

import com.petpal.petpalservice.mapper.DocumentMapper;
import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.DocumentResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.model.entity.MedicalRecord;
import com.petpal.petpalservice.repository.DocumentRepository;
import com.petpal.petpalservice.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @InjectMocks
    private DocumentService documentService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private DocumentMapper documentMapper;

    @Test
    public void testAddDocument() {
        //Arrange
        DocumentRequestDto dto = new DocumentRequestDto();
        dto.setLink("link");
        dto.setIdRecord(1);

        MedicalRecord record = new MedicalRecord();
        record.setIdRecord(1);

        Document doc = new Document();
        doc.setLink(dto.getLink());
        doc.setRecord(record);

        when(medicalRecordRepository.findById(dto.getIdRecord())).thenReturn(Optional.of(record));
        when(documentRepository.save(doc)).thenReturn(doc);

        //Act
        Document result = documentService.addDocument(dto);

        //Assert
        assertEquals(result.getRecord(), result.getRecord());
        assertEquals(result.getLink(), result.getLink());
    }

    @Test
    public void testAddDocument_RecordNotFound() {
        //Arrange
        DocumentRequestDto dto = new DocumentRequestDto();
        dto.setLink("link");
        dto.setIdRecord(1);

        when(medicalRecordRepository.findById(dto.getIdRecord())).thenReturn(Optional.empty());

        //Act
        try {
            documentService.addDocument(dto);
        } catch (Exception e) {
            //Assert
            assertEquals("Record not found", e.getMessage());
        }
    }

    @Test
    public void testGetDocumentsByRecord() {
        //Arrange
        int id = 1;

        //Act
        Document doc1 = new Document();
        doc1.setIdDocument(1);
        Document doc2 = new Document();
        doc2.setIdDocument(2);
        List<Document> documents = Arrays.asList(doc1, doc2);
        when(documentRepository.findByRecord(id)).thenReturn(documents);

        DocumentResponseDto documentResponseDto1 = new DocumentResponseDto();
        DocumentResponseDto documentResponseDto2 = new DocumentResponseDto();
        documentResponseDto1.setId(doc1.getIdDocument());
        documentResponseDto2.setId(doc2.getIdDocument());

        List<DocumentResponseDto>documentResponseDtoList = Arrays.asList(documentResponseDto1, documentResponseDto2);
        when(documentMapper.convertToDtoList(documents)).thenReturn(documentResponseDtoList);

        List<DocumentResponseDto> result = documentService.getDocumentsByRecord(id);
        //Assert
        assertNotNull(result);
        assertEquals(documentResponseDtoList.size(), result.size());
    }


}
