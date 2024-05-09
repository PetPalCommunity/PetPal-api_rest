package com.petpal.petpalservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.CommunityMapper;
import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.repository.CommunityRepository;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    @Transactional(readOnly = true)
    public List<CommunityResponseDTO> getAllCommunities(){
        List<Community> communities = communityRepository.findAll();
        return communityMapper.convertToListDTO(communities);
    }
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
        community.setTags(communityRequestDTO.getTags());
        community.setCreationDate(LocalDate.now());
        communityRepository.save(community);
        return communityMapper.convertToDTO(community);
    }
    @Transactional
    public void deleteCommunity(Long id){
        communityRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllCommunities(){
        communityRepository.deleteAll();
    }
    @Transactional
    public CommunityResponseDTO updateCommunity(Long id, CommunityRequestDTO communityRequestDTO){
        Community community = communityRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("La comunidad no existe"));
        community.setName(communityRequestDTO.getName());
        community.setDescription(communityRequestDTO.getDescription());
        community.setCountFollowers(communityRequestDTO.getCountFollowers());
        // community.setTags(String.join(", ", communityRequestDTO.getTags()));
        community.setTags(communityRequestDTO.getTags());
        community.setCreationDate(LocalDate.now());
        community = communityRepository.save(community);
        return communityMapper.convertToDTO(community);
    }
}
