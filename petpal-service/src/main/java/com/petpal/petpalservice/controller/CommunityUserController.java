package com.petpal.petpalservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserResponseDTO;
import com.petpal.petpalservice.service.CommunityUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/community-user")
@AllArgsConstructor
public class CommunityUserController {
    private final CommunityUserService communityUserService;
    //task2
    @PostMapping
    public ResponseEntity<CommunityUserResponseDTO> createCommunityUser(
        @Validated @RequestBody CommunityUserRequestDTO communityUserRequestDTO){
        CommunityUserResponseDTO createdCommunityUser = communityUserService.createCommunityUser(communityUserRequestDTO);
        return new ResponseEntity<>(createdCommunityUser, HttpStatus.CREATED);
    }
}
