package com.petpal.petpalservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.mapper.CommunityUserMapper;
import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserResponseDTO;
import com.petpal.petpalservice.model.entities.CommunityUser;
import com.petpal.petpalservice.repository.CommunityUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityUserService {
    private CommunityUserRepository communityUserRepository;
    private final CommunityUserMapper communityUserMapper = new CommunityUserMapper();
    
    //task 2 un community user se crea cuando un petowner se une a una comunidad
    @Transactional
    public CommunityUserResponseDTO createCommunityUser(CommunityUserRequestDTO communityUserRequestDTO){
        CommunityUser communityUser =  communityUserMapper.convertToEntity(communityUserRequestDTO);
        communityUserRepository.save(communityUser);
        return communityUserMapper.convertToDTO(communityUser);
    }
}

