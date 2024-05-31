package com.petpal.petpalservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.CommunityMapper;
import com.petpal.petpalservice.mapper.CommunityUserMapper;
import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserResponseDTO;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.model.entities.CommunityUser;
import com.petpal.petpalservice.repository.CommunityRepository;
import com.petpal.petpalservice.repository.CommunityUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CommunityServiceTest {

  @Mock
  private CommunityRepository communityRepository;
  @Mock
  private CommunityUserRepository communityUserRepository;
  @Mock
  private CommunityMapper communityMapper;
  @Mock
  private CommunityUserMapper communityUserMapper;
  @InjectMocks
  private CommunityService communityService;
  @InjectMocks
  private CommunityUserService communityUserService;

  @Test
  public void createCommunity() {
    CommunityRequestDTO communityRequestDTO = new CommunityRequestDTO();
    communityRequestDTO.setName("CatLovers");
    communityRequestDTO.setDescription("Cat Lovers Community");
    communityRequestDTO.setTags(Arrays.asList("lovely", "cats"));

    Community community = new Community();
    community.setId(1L);
    community.setName(communityRequestDTO.getName());
    community.setDescription(communityRequestDTO.getDescription());
    community.setTags(communityRequestDTO.getTags());

    //mocking the repository
    when(communityRepository.save(any(Community.class))).thenReturn(community);
    //mocking mapper
    when(communityMapper.convertToEntity(any(CommunityRequestDTO.class))).thenReturn(community);
    when(communityMapper.convertToDTO(any(Community.class))).thenReturn(new CommunityResponseDTO(community.getId(), community.getName(), community.getDescription(), community.getTags(), community.getCreationDate(), List.of() ));
    
    //Act
    CommunityResponseDTO communityResponseDTO = communityService.createCommunity(communityRequestDTO);

    //Assert 
    Assert.notNull(communityResponseDTO, "CommunityResponseDTO is null");
    assertEquals(communityRequestDTO.getName(), communityResponseDTO.getName());
    assertEquals(communityRequestDTO.getDescription(), communityResponseDTO.getDescription());
    assertEquals(communityRequestDTO.getTags(), communityResponseDTO.getTags());

    //Verify that convertToEntity was called
    verify(communityMapper, times(1)).convertToEntity(communityRequestDTO);
    //Verify that save was called
    verify(communityRepository, times(1)).save(community);
    //Verify that convertToDTO was called
    verify(communityMapper, times(1)).convertToDTO(community);

  }

  @Test
  void createCommunityUserSuccessfully() {
    //Arrange
    CommunityUserRequestDTO request = new CommunityUserRequestDTO();
    request.setRole("ADMIN");
    request.setUserName("saelcc03");

    Community community = new Community();
    community.setId(1L);
    community.setName("CatLovers");
    community.setDescription("Cat Lovers Community");
    community.setTags(Arrays.asList("lovely", "cats"));
    community.setCommunityUsers(new ArrayList<>());

    CommunityUser user = new CommunityUser();
    user.setId(1L);
    user.setRole(request.getRole());
    user.setUserName(request.getUserName());
    user.setCommunity(community);

    // Mocking the repository
    when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
    // Mocking the mapper
    when(communityUserMapper.convertToEntity(request)).thenReturn(user);
    community.getCommunityUsers().add(user);
    when(communityUserRepository.save(user)).thenReturn(user);
    when(communityUserMapper.convertToDTO(user)).thenReturn(new CommunityUserResponseDTO(user.getUserName(), user.getRole(), user.getCommunity().getId()));
    // Act
    CommunityUserResponseDTO result = communityUserService.createCommunityUser(1L, request);

    // Assert
    Assert.notNull(result, "CommunityUserResponseDTO is null");
    assertEquals(request.getUserName(), result.getUsername());
    assertEquals(request.getRole(), result.getRole());
    assertEquals(community.getId(), result.getCommunityId());

    // verify
    verify(communityRepository, times(1)).findById(1L);
    verify(communityUserMapper, times(1)).convertToEntity(request);
    verify(communityUserRepository, times(1)).save(user);
    verify(communityUserMapper, times(1)).convertToDTO(user);
  }
  @Test
  void createCommunityUserUnsuccessfully(){
    //Arrange
    CommunityUserRequestDTO request = new CommunityUserRequestDTO();
    request.setRole("ADMIN");
    request.setUserName("saelcc03");


    // Mocking the repository
    when(communityRepository.findById(1L)).thenReturn(Optional.empty());
    // Act
    assertThrows(ResourceNotFoundException.class, () -> communityUserService.createCommunityUser(1L, request));

    verify(communityUserMapper, never()).convertToEntity(request);
    verify(communityUserRepository, never()).save(any());
    verify(communityUserMapper, never()).convertToDTO(any());

  }
}

