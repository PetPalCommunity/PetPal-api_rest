package com.petpal.petpalservice.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.CommunityUserMapper;
import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserResponseDTO;
import com.petpal.petpalservice.model.entities.CommunityUser;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.repository.CommunityRepository;
import com.petpal.petpalservice.repository.CommunityUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityUserService {
  private CommunityUserRepository communityUserRepository;
  private CommunityUserMapper communityUserMapper;
  private CommunityRepository communityRepository;

  //task 2 un community user se crea cuando un petowner se une a una comunidad
  @Transactional
  public CommunityUserResponseDTO createCommunityUser(Long id, CommunityUserRequestDTO communityUserRequestDTO){
    Community community = communityRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("No existe una comunidad con dicho id: " + id));
    CommunityUser communityUser =  communityUserMapper.convertToEntity(communityUserRequestDTO);
    community.setCountFollowers(community.getCountFollowers() + 1);
    community.getCommunityUsers().add(communityUser);
    communityRepository.save(community);
    communityUser.setCommunity(community);
    communityUserRepository.save(communityUser);
    return communityUserMapper.convertToDTO(communityUser);
  }
}

