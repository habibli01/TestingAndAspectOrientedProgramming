package com.example.registration.controller;

import com.example.registration.entity.RegistrationDTO;
import com.example.registration.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    void testList() throws Exception {
        // Given
        when(registrationService.list()).thenReturn(Collections.emptyList());

        // When and Then
        mockMvc.perform(get("/registration/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteRegistration() throws Exception {
        // Given
        Long id = 1L;
        doNothing().when(registrationService).delete(id);

        // When and Then
        mockMvc.perform(delete("/registration/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testRegister() throws Exception {
        // Given
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setRole("admin");

        // When and Then
        mockMvc.perform(post("/registration/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"role\": \"admin\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        // Given
        Long id = 1L;
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setRole("admin");

        // When and Then
        mockMvc.perform(put("/registration/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"role\": \"admin\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testPatch() throws Exception {
        // Given
        Long id = 1L;
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setRole("admin");

        // When and Then
        mockMvc.perform(patch("/registration/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\"}"))
                .andExpect(status().isOk());
    }
}
