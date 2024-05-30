package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.entity.Document;
import com.petpal.petpalservice.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class DocumentController {
    private final DocumentService service;

    @PostMapping("/addDocument")
    public ResponseEntity<Document> addDocument(@RequestBody DocumentRequestDto dto){
        Document created = service.addDocument(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}/documents")
    public ResponseEntity<?> getDocumentsByRecord(@PathVariable int id){
        return ResponseEntity.ok(service.getDocumentsByRecord(id));
    }
}
