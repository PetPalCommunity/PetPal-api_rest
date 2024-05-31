package com.petpal.petpalservice.PetOwners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.*;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Followers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PetOwnerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterPetOwner() throws Exception {
        PetOwnerRequestDto petOwnerRequestDto = new PetOwnerRequestDto("Test", 30, "Male", 123456789, "test@test.com", "password");
        String json = asJsonString(petOwnerRequestDto);
        System.out.println(json);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/petowners/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testSignIn() throws Exception {
        SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setOwnerEmail("sebas@gmail.com");
        signInRequestDto.setOwnerPassword("123");


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/petowners/signin")
                        .content(asJsonString(signInRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllPetOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/petowners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testFollow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/petowners/follow?followerId=1&followedId=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testUnfollow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/petowners/unfollow?followerId=1&followedId=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetFollowers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/petowners/followers?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}