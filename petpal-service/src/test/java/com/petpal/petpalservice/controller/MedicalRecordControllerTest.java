package com.petpal.petpalservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.DocumentRequestDto;
import com.petpal.petpalservice.model.dto.MedicalRecordRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.sql.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testCreateRecord() throws Exception {
        MedicalRecordRequestDto recordRequestDto = new MedicalRecordRequestDto();

        recordRequestDto.setIdPet(1);
        recordRequestDto.setDate(Date.valueOf("2021-05-05"));
        recordRequestDto.setKind("kind");
        recordRequestDto.setDescription("observations");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/records/addRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recordRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetRecordsByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/records/viewRecords")
                        .param("date", "2021-05-05"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/records/viewAllRecords"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddDocument() throws Exception {
        DocumentRequestDto documentRequestDto = new DocumentRequestDto();
        documentRequestDto.setLink("link");
        documentRequestDto.setIdRecord(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/records/addDocument")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(documentRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetDocumentsByRecord() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/records/1/documents"))
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
