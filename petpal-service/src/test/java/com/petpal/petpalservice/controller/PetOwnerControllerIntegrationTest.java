package com.petpal.petpalservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.DayOfWeek;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class PetOwnerControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void updateVisibility() throws Exception{
    VisibilityRequestDto dto = new VisibilityRequestDto();
    dto.setPetVisible(false);
    dto.setPostVisible(false);
    dto.setProfileVisible(false);
    mockMvc.perform(MockMvcRequestBuilders.put("/petowners/privacy")
      .contentType(MediaType.APPLICATION_JSON)
      .content(asJsonString(dto))
      .andExpect(MockMvcResultMatchers.status().isOk()));
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

