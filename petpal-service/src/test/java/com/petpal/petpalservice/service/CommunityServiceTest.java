package com.petpal.petpalservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import com.petpal.petpalservice.mapper.CommunityMapper;
import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityResponseDTO;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.repository.CommunityRepository;
import com.petpal.petpalservice.repository.CommunityUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CommunityServiceTest {

  @Mock
  private CommunityRepository communityRepository;
  @Mock
  private CommunityUserRepository communityUserRepository;
  @Mock
  private CommunityMapper communityMapper;
  @InjectMocks
  private CommunityService communityService;

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
    when(communityMapper.convertToDTO(any(Community.class))).thenReturn(new CommunityResponseDTO(community.getId(), community.getName(), community.getDescription(), community.getTags(), community.getCreationDate(), null));
    
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

  // @Test
  // public void testGetAllCommunities() {
  //   when(communityRepository.findAll()).thenReturn(Arrays.asList(new Community(), new Community()));
  //   List<CommunityResponseDTO> communities = communityService.getAllCommunities();
  //   assertEquals(2, communities.size());
  // }

}
