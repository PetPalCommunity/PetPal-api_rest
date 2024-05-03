package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.ReviewVetRequestDto;
import com.petpal.petpalservice.model.entity.ReviewVet;
import com.petpal.petpalservice.service.ReviewVetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vets")
public class ReviewVetController {
    private final ReviewVetService service;

    public ReviewVetController(ReviewVetService service) {
        this.service = service;
    }

    @PostMapping("/postReview")
    public ResponseEntity<ReviewVet> createReview(@RequestBody ReviewVetRequestDto dto) {
        ReviewVet created = service.createReview(dto);
        return ResponseEntity.ok(created);
    }
}