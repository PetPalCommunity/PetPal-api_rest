package com.petpal.petpalservice.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.dto.CommunityRequestDTO;
import com.petpal.petpalservice.model.dto.CommunityResponseDTO;
import com.petpal.petpalservice.model.dto.UserResponseDTO;
import com.petpal.petpalservice.service.CommunityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

  private final CommunityService communityService;

  @PostMapping("/create")
  public ResponseEntity<CommunityResponseDTO> createCommunity(@Validated @RequestBody 
                                                              CommunityRequestDTO communityRequestDTO){
    CommunityResponseDTO createdCommunity = communityService.createCommunity(communityRequestDTO);
    return new ResponseEntity<>(createdCommunity, HttpStatus.CREATED);
  }

  @GetMapping("/{name}")
  public ResponseEntity<CommunityResponseDTO> getCommunitytByName(@PathVariable String name){
    CommunityResponseDTO community = communityService.getCommunityByName(name);
    return new ResponseEntity<>(community, HttpStatus.OK);
  }

  @GetMapping("/search")
    public ResponseEntity<Page<CommunityResponseDTO>> searchCommunities(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String tag,  
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size){
        Page<CommunityResponseDTO> response = communityService.searchCommunities(name,tag,page,size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


  @GetMapping("/user/{alias}")
  public ResponseEntity<List<CommunityResponseDTO>> getCommunitiesByUser(@PathVariable String alias){
    List<CommunityResponseDTO> communities = communityService.getCommunitiesByUser(alias);
    return new ResponseEntity<>(communities, HttpStatus.OK);
  }

  @GetMapping("/{name}/users")
  public ResponseEntity<List<UserResponseDTO>> getMembersByCommunity(@PathVariable String name){
    List<UserResponseDTO> users = communityService.getMembersByCommunity(name);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PostMapping("/{name}/join")
  public ResponseEntity<String> joinCommunity(@PathVariable String name){
    communityService.joinCommunity(name);
    return new ResponseEntity<>("Se ha unido a la comunidad "+name, HttpStatus.OK);
  }

  @DeleteMapping("/{name}/leave")
  public ResponseEntity<String> leaveCommunity(@PathVariable String name){
    communityService.leaveCommunity(name);
    return new ResponseEntity<>("Ha abandonado la comunidad "+name, HttpStatus.OK);
  }

  
}
