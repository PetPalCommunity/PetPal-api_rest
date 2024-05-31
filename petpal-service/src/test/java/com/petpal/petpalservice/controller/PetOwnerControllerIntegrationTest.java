package com.petpal.petpalservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
public class PetOwnerControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private PetOwnerRepository repository;
  @BeforeEach
  public void setUp() {
    repository.deleteAll();
    PetOwner petOwner = new PetOwner();
    petOwner.setOwnerName("Sael");
    petOwner.setOwnerAge(20);
    petOwner.setOwnerSex("Masculino");
    petOwner.setOwnerPhone(123456789);
    petOwner.setOwnerEmail("saelcc03@gmail.com");
    petOwner.setOwnerPassword("123");
    repository.save(petOwner);
  }

  @Test
  public void updateVisibility() throws Exception{
    VisibilityRequestDto dto = new VisibilityRequestDto();
    dto.setPetVisible(false);
    dto.setPostVisible(false);
    dto.setProfileVisible(false);
    String email = "saelcc03@gmail.com";
    mockMvc.perform(MockMvcRequestBuilders.patch("/petowners/privacy")
      .param("email", email)
      .contentType(MediaType.APPLICATION_JSON)
      .content(asJsonString(dto)))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

