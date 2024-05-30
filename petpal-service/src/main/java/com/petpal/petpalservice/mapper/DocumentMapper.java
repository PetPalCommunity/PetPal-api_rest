package com.petpal.petpalservice.mapper;

import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.DocumentResponseDto;
import com.petpal.petpalservice.model.entity.Document;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentMapper {
    private final ModelMapper modelMapper;
    public Document convertToEntity(DocumentRequestDto dto) {
        return modelMapper.map(dto, Document.class);
    }

    public DocumentResponseDto convertToDto(Document document) {
        return modelMapper.map(document, DocumentResponseDto.class);
    }

    public List<DocumentResponseDto>convertToDtoList(List<Document> documents){
        return documents.stream()
                .map(this::convertToDto)
                .toList();
    }

}
