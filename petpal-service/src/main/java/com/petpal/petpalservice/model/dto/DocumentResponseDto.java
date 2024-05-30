package com.petpal.petpalservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class DocumentResponseDto{
    private int id;
    private int idRecord;
    String link;
}