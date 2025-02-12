package com.platformbuilders.controleescolar.studentaccess.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.service.StudentAccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentAccessController.class)
public class StudentAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentAccessService studentAccessService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerEntry_ExistingStudentId_ReturnsCreatedStudentAccessDTO() throws Exception {
        Long studentId = 1L;
        StudentAccessDTO responseDTO = new StudentAccessDTO();
        responseDTO.setStudentId(studentId);
        responseDTO.setStatus("ENTRY_REGISTERED");

        when(studentAccessService.registerEntry(studentId)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/access/entry/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(studentId));
    }

    @Test
    void registerEntry_NonExistingStudentId_ReturnsNotFound() throws Exception {
        Long studentId = 1L;
        when(studentAccessService.registerEntry(studentId))
                .thenThrow(new com.platformbuilders.controleescolar.student.exception.StudentNotFoundException());

        mockMvc.perform(post("/api/access/entry/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void registerExit_ExistingStudentId_ReturnsStudentAccessDTO() throws Exception {
        Long studentId = 1L;
        StudentAccessDTO responseDTO = new StudentAccessDTO();
        responseDTO.setStudentId(studentId);
        responseDTO.setStatus("EXIT_REGISTERED");

        when(studentAccessService.registerExit(studentId)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/access/exit/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(studentId));
    }

    @Test
    void registerExit_NonExistingStudentId_ReturnsNotFound() throws Exception {
        Long studentId = 1L;
        when(studentAccessService.registerExit(studentId))
                .thenThrow(new com.platformbuilders.controleescolar.student.exception.StudentNotFoundException());

        mockMvc.perform(post("/api/access/exit/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }
}