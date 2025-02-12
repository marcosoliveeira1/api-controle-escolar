package com.platformbuilders.controleescolar.school.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformbuilders.controleescolar.school.api.controller.SchoolController;
import com.platformbuilders.controleescolar.school.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.school.service.SchoolService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SchoolService schoolService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSchool_ValidInput_ReturnsCreatedSchoolDTO() throws Exception {
        SchoolDTO requestDTO = SchoolDTO.builder()
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();
        SchoolDTO responseDTO = SchoolDTO.builder()
                .id(1L)
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();

        when(schoolService.create(any(SchoolDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/schools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("School Name"));
    }

    @Test
    void createSchool_InvalidInput_ReturnsBadRequest() throws Exception {
        SchoolDTO invalidDTO = SchoolDTO.builder()
                .name("") // Invalid: Blank field
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();

        mockMvc.perform(post("/api/schools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO))
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void findSchool_ExistingId_ReturnsSchoolDTO() throws Exception {
        SchoolDTO responseDTO = SchoolDTO.builder()
                .id(1L)
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();

        when(schoolService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/schools/1")
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("School Name"));
    }

    @Test
    void findSchool_NonExistingId_ReturnsNotFound() throws Exception {
        when(schoolService.findById(1L)).thenThrow(new com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException());

        mockMvc.perform(get("/api/schools/1")
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void listSchools_ReturnsPageOfSchoolDTOs() throws Exception {
        SchoolDTO schoolDTO = SchoolDTO.builder()
                .id(1L)
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();
        PageImpl<SchoolDTO> pageResponse = new PageImpl<>(Collections.singletonList(schoolDTO), Pageable.ofSize(10), 1);

        when(schoolService.findAll(any(Pageable.class))).thenReturn(pageResponse);

        mockMvc.perform(get("/api/schools")
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void updateSchool_ExistingIdAndValidInput_ReturnsUpdatedSchoolDTO() throws Exception {
        SchoolDTO requestDTO = SchoolDTO.builder()
                .name("Updated School Name")
                .cnpj("76.662.830/0001-46")
                .address("Updated School Address")
                .phone("1234-5678")
                .studentsPerClassroom(25)
                .build();
        SchoolDTO responseDTO = SchoolDTO.builder()
                .id(1L)
                .name("Updated School Name")
                .cnpj("76.662.830/0001-46")
                .address("Updated School Address")
                .phone("1234-5678")
                .studentsPerClassroom(25)
                .build();

        when(schoolService.update(any(Long.class), any(SchoolDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/schools/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated School Name"));
    }

    @Test
    void updateSchool_NonExistingId_ReturnsNotFound() throws Exception {
        SchoolDTO requestDTO = SchoolDTO.builder()
                .name("Updated School Name")
                .cnpj("76.662.830/0001-46")
                .address("Updated School Address")
                .phone("1234-5678")
                .studentsPerClassroom(25)
                .build();

        when(schoolService.update(any(Long.class), any(SchoolDTO.class)))
                .thenThrow(new com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException());

        mockMvc.perform(put("/api/schools/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void deleteSchool_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/schools/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_NonExistingId_ReturnsNotFound() throws Exception { // Corrected method name to deleteSchool
        Mockito.doThrow(new com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException())
                .when(schoolService).delete(1L);

        mockMvc.perform(delete("/api/schools/1")
                        .accept(MediaType.APPLICATION_JSON)) // ADDED: Accept JSON - although should be no content
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }
}