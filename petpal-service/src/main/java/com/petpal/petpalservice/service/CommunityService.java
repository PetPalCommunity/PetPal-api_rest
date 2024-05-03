package com.petpal.petpalservice.service;

import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.CommunityMapper;
import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.repository.CommunityRepository;


public class CommunityService {
    private CommunityRepository communityRepository;
    private final CommunityMapper communityMapper = new CommunityMapper();
    //task1 get comunidad
    @Transactional(readOnly = true)
    public CommunityResponseDTO getCommunityByName(String name){
        Community community = communityRepository.findCommunityByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("No existe una comunidad con dicho nombre"));
        return communityMapper.convertToDTO(community);
    }
    //task 3 crear comunidad
    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO){
        Community community = communityMapper.convertToEntity(communityRequestDTO);
        community.setCountFollowers(0L);
        communityRepository.save(community);
        return communityMapper.convertToDTO(community);
    }
}
