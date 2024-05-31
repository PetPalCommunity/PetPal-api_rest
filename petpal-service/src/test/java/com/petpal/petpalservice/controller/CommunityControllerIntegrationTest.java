package com.petpal.petpalservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.DTO.CommunityRequestDTO;
import com.petpal.petpalservice.model.DTO.CommunityUserRequestDTO;
import com.petpal.petpalservice.model.entities.Community;
import com.petpal.petpalservice.repository.CommunityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CommunityControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CommunityRepository communityRepository;
  
  @BeforeEach
    public void setup() {
        communityRepository.deleteAll();
        Community community = new Community();
        community.setName("Ninjahams");
        community.setDescription("Comunidad de hamsters ninja");
        community.setTags(List.of("family-friendly", "hamsters", "ninja"));
        communityRepository.save(community);
    }
  //-----CREATE COMMUNITY-----
  @Test
  public void createCommunity() throws Exception {
    CommunityRequestDTO communityRequestDTO = new CommunityRequestDTO();
    communityRequestDTO.setName("Ninjacats");
    communityRequestDTO.setDescription("Comunidad de gatos ninja");
    communityRequestDTO.setTags(List.of("family-friendly", "cats", "ninja"));
    mockMvc.perform(MockMvcRequestBuilders.post("/community")
      .contentType(MediaType.APPLICATION_JSON)
      .content(asJsonString(communityRequestDTO))
    ).andExpect(MockMvcResultMatchers.status().isCreated());
  }
  //-----GET ALL COMMUNITIES-----
  @Test
  public void getAllCommunities() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/community")
      .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }
  //-----GET COMMUNITY BY NAME-----
  @Test
  public void getCommunityByName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/community/{name}", "Ninjahams")
      .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }
  //-----CREATE COMMUNITY USER-----
  @Test
  public void createCommunityUser() throws Exception {
    CommunityUserRequestDTO user = new CommunityUserRequestDTO();
    user.setRole("admin");
    user.setUserName("Sam");
    Community community = communityRepository.findCommunityByName("Ninjahams").orElseThrow();
    mockMvc.perform(MockMvcRequestBuilders.post("/community/{id}/community-user", community.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content(asJsonString(user))
    ).andExpect(MockMvcResultMatchers.status().isCreated());
  }


  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
