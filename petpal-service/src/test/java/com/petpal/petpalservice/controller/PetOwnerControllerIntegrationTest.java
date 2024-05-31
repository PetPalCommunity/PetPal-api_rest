package com.petpal.petpalservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.PersonalInfoDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
    PetOwner owner = new PetOwner();
    owner.setOwnerName("Sael");
    owner.setOwnerAge(20);
    owner.setOwnerSex("Masculino");
    owner.setOwnerPhone(123456789);
    owner.setOwnerEmail("saelcc03@gmail.com");
    owner.setOwnerPassword("123");
    repository.save(owner);
  }

  @Test
  public void testUpdatePersonalInfo() throws Exception {
    PersonalInfoDto dto = new PersonalInfoDto();
    dto.setOwnerDescription("Soy un chico alegre");
    dto.setOwnerLocation("Lima");
    dto.setOwnerFullName("Sael Cc");
    dto.setOwnerContactInfo("123456789");
    mockMvc.perform(MockMvcRequestBuilders.patch("/petowners/personalinfo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(asJsonString(dto))
      .param("email", "saelcc03@gmail.com"));
  }

  @Test
  public void testUpload() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "meli_meli.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());

    mockMvc.perform(multipart("/petowners/upload")
      .file(file)
      .param("email", "saelcc03@gmail.com")
      .contentType(MediaType.MULTIPART_FORM_DATA))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }
  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @Test 
  public void testLoadImage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/petowners/{filename}", "meli_meli.png"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }
      
}
