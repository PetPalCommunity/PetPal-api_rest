package com.petpal.petpalservice.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.CommunityMapper;
import com.petpal.petpalservice.mapper.UserMapper;
import com.petpal.petpalservice.model.dto.CommunityRequestDTO;
import com.petpal.petpalservice.model.dto.CommunityResponseDTO;
import com.petpal.petpalservice.model.dto.UserResponseDTO;
import com.petpal.petpalservice.model.entity.Community;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.repository.CommunityRepository;
import com.petpal.petpalservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
  private final CommunityRepository communityRepository;
  private final UserRepository userRepository;
  private final CommunityMapper communityMapper;
  private final UserMapper userMapper;
  private final UserService userService;

  //task 3 crear comunidad
    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDTO communityRequestDTO){
        User user = userService.getUserByAuth();
        boolean exists = communityRepository.existsByName(communityRequestDTO.getName());
        if (exists) {
            throw new ResourceNotFoundException("Ya existe una comunidad con el nombre "+communityRequestDTO.getName());
        }

        Community community = communityMapper.convertToEntity(communityRequestDTO);
        if (communityRequestDTO.getTags() != null) {
          String concatenatedTags = String.join("$", communityRequestDTO.getTags());
          community.setTags(concatenatedTags);
        }
        community.setCreationDate(LocalDate.now());
        community.setCountMembers(1L);
        community.setOwnerAlias(user.getAlias());
        
        Set<User> usersAux = new HashSet<>();
        usersAux.add(user);
        community.setUsers(usersAux);
        communityRepository.save(community);
        
        return communityMapper.convertToDTO(community);
    }

    @Transactional(readOnly = true)
    public Page<CommunityResponseDTO> searchCommunities(String name,String tag, int page, int size){
      Pageable pageable = PageRequest.of(page, size);
      System.out.println("name: "+name);
      Page<Community> communities = communityRepository.findCommunitiesByNameContaining(name, tag,pageable);
      return communities.map(communityMapper::convertToDTO);
    }

    @Transactional(readOnly = true)
    public CommunityResponseDTO getCommunityByName(String name){
      Community community = communityRepository.findCommunityByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("No existe una comunidad con el nombre "+name));
      return communityMapper.convertToDTO(community);
    }

    @Transactional(readOnly = true)
    public List<CommunityResponseDTO> getCommunitiesByUser(String alias)  {
      User user = userRepository.findUserByAlias(alias)
                        .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el alias "+alias));
      List<Community> communities = communityRepository.findCommunitiesByUserId(user.getId());
      return communityMapper.convertToListDTO(communities);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getMembersByCommunity(String name){
      List<User> users = communityRepository.findUsersByCommunityName(name);
      return userMapper.convertToListDTO(users);
    }

    @Transactional
    public void joinCommunity(String name){
      User user = userService.getUserByAuth();
      Community community = communityRepository.findCommunityByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("No existe una comunidad con el nombre "+name));
      boolean exists = communityRepository.existByUserIdAndCommunityId(user.getId(), community.getId());
      if (exists) {
        throw new IllegalArgumentException("Ya eres miembro de la comunidad "+name);
      }
      Set<User> users = community.getUsers();
      users.add(user);
      community.setUsers(users);
      community.setCountMembers(community.getCountMembers()+1);
      communityRepository.save(community);
    }

    @Transactional
    public void leaveCommunity(String name){
      User user = userService.getUserByAuth();
      Community community = communityRepository.findCommunityByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("No existe una comunidad con el nombre "+name));
      if (community.getOwnerAlias().equals(user.getAlias())) {
        throw new ResourceNotFoundException("No puedes abandonar la comunidad porque eres el dueño");
      }
      boolean exists = communityRepository.existByUserIdAndCommunityId(user.getId(), community.getId());
      if (!exists) {
        throw new ResourceNotFoundException("No eres miembro de la comunidad "+name);
      }
      communityRepository.deleteByUserIdAndCommunityId(user.getId(), community.getId());
      community.setCountMembers(community.getCountMembers() - 1);
      communityRepository.save(community);
    }
}
