package com.petpal.petpalservice.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.service.CommunityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/community")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    //task1
    @GetMapping
    public ResponseEntity<List<CommunityResponseDTO>> getAllCommunities(){
        List<CommunityResponseDTO> communities = communityService.getAllCommunities();
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<CommunityResponseDTO> getCommunitytByName(@PathVariable String name){
        CommunityResponseDTO community = communityService.getCommunityByName(name);
        return new ResponseEntity<>(community, HttpStatus.OK);
    }
    //task3
    @PostMapping
    public ResponseEntity<CommunityResponseDTO> createCommunity(
        @Validated @RequestBody CommunityRequestDTO communityRequestDTO){
        CommunityResponseDTO createdCommunity = communityService.createCommunity(communityRequestDTO);
        return new ResponseEntity<>(createdCommunity, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> deleteCommunity(@PathVariable Long id){
        communityService.deleteCommunity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/")
    public ResponseEntity<CommunityResponseDTO>deleteAllCommunities(){
        communityService.deleteAllCommunities();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommunityResponseDTO> updateCommunity(@PathVariable Long id, @Validated @RequestBody CommunityRequestDTO communityRequestDTO){
        CommunityResponseDTO updatedCommunity = communityService.updateCommunity(id, communityRequestDTO);
        return new ResponseEntity<>(updatedCommunity, HttpStatus.OK);
    }

}
