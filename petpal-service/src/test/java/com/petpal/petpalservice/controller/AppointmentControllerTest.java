package com.petpal.petpalservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.AppointmentRequestDto;
import com.petpal.petpalservice.model.dto.PaymentRequestDto;
import com.petpal.petpalservice.repository.AppointmentRepository;
import com.petpal.petpalservice.repository.PaymentRepository;
import com.petpal.petpalservice.repository.PetRepository;
import com.petpal.petpalservice.service.AppointmentService;
import com.petpal.petpalservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.sql.Date;
import java.sql.Time;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testCreateAppointment() throws Exception {
        AppointmentRequestDto dto = new AppointmentRequestDto();

        dto.setIdPet(0);
        dto.setIdVet(0);
        dto.setDate(Date.valueOf("2025-01-19"));
        dto.setTime(Time.valueOf("01:00:00"));
        dto.setReason("hi");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointment/createAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateAppointmentConfirmation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointment/confirmAppointment")
                        .param("id", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testFindAppointmentByPet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/appointment/0/appointments"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreatePayment() throws Exception {

        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setIdAppointment(18);
        dto.setAmount(100);
        dto.setDate(Date.valueOf("2025-01-29"));
        dto.setDescription("a description");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payment/createPayment")
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
