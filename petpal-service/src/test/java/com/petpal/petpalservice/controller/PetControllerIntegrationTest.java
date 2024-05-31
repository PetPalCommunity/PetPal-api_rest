package com.petpal.petpalservice.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;
import com.petpal.petpalservice.model.dto.ReminderUpdateRequestDto;
import com.petpal.petpalservice.model.dto.petUpdateRequestDto;

import java.time.DayOfWeek;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreatePet() throws Exception {
        PetRequestDto petRequestDto = new PetRequestDto();

        petRequestDto.setPetName("Firulais");
        petRequestDto.setPetSpecies("Perro");
        petRequestDto.setPetBreed("Golden Retriever");
        petRequestDto.setPetAge(5);
        petRequestDto.setPetSex("Macho");
        petRequestDto.setOwnerId(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pets/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateReminder() throws Exception{
        ReminderRequestDto reminderRequestDto = new ReminderRequestDto();
        reminderRequestDto.setIdPet(2);
        reminderRequestDto.setReminderName("Vacuna");
        reminderRequestDto.setReminderDescription("Vacuna contra la rabia");
        reminderRequestDto.setReminderTime("10:00:00");
        reminderRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pets/reminders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reminderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void testDeletePet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/pets/{ownerId}/{id}", 1, 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void testDeleteReminder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/pets/reminders/{petId}/{id}", 2, 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void testGetPetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/{ownerId}/{id}", 1, 2))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetPetsByOwnerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/{ownerId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetReminderByPetId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/reminders/{petId}/{id}", 2,2))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetRemindersByPetId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pets/reminders/{petId}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdatePet() throws Exception {
        petUpdateRequestDto petRequestDto = new petUpdateRequestDto();

        petRequestDto.setPetName("Fido");
        petRequestDto.setPetSpecies("Perro");
        petRequestDto.setPetBreed("Golden Retriever");
        petRequestDto.setPetAge(5);
        petRequestDto.setPetSex("Macho");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/pets/{ownerId}/{id}", 1, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(petRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testUpdateReminder() throws Exception {
        ReminderUpdateRequestDto reminderRequestDto = new ReminderUpdateRequestDto();
        reminderRequestDto.setReminderName("Vacunaton");
        reminderRequestDto.setReminderTime("12:00:00");
        reminderRequestDto.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.SATURDAY));


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/pets/reminders/{petId}/{id}", 2, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reminderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testSendEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/pets/reminders/sendEmail") 
                                                .param("idPet", "2")
                                                .param("date", "2024-05-30")
                                                .param("time", "18:30:00"))
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
