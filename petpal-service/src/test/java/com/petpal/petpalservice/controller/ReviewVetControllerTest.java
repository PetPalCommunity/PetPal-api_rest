package com.petpal.petpalservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.ReviewVetRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewVetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void TestCreateReview() throws Exception {
        ReviewVetRequestDto reviewVetRequestDto = new ReviewVetRequestDto();

        reviewVetRequestDto.setIdVet(1);
        reviewVetRequestDto.setIdPetOwner(1);
        reviewVetRequestDto.setStars(BigDecimal.valueOf(5));
        reviewVetRequestDto.setComment("Mal servicio");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vets/postReview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reviewVetRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestGetReviewsByVetId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vets/1/reviews"))
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