package com.petpal.petpalservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.service.CommunityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/community")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    //task1
    @GetMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> getCommunitytByName(@PathVariable String name){
        CommunityResponseDTO community = communityService.getCommunityByName(name);
        return new ResponseEntity<>(community, HttpStatus.OK);
    }
    //task3
    @PostMapping
    public ResponseEntity<CommunityResponseDTO> createCommunity(
        @Validated @RequestBody
    )
}
