package com.platformbuilders.controleescolar.student.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformbuilders.controleescolar.student.api.dto.StudentDTO;
import com.platformbuilders.controleescolar.student.service.StudentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // Import print()

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStudent_ValidInput_ReturnsCreatedStudentDTO() throws Exception {
        StudentDTO requestDTO = StudentDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();
        StudentDTO responseDTO = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        when(studentService.create(any(StudentDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void createStudent_InvalidInput_ReturnsBadRequest() throws Exception {
        StudentDTO invalidDTO = StudentDTO.builder()
                .firstName("") // Invalid: Blank field
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void findStudent_ExistingId_ReturnsStudentDTO() throws Exception {
        StudentDTO responseDTO = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        when(studentService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/students/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void findStudent_NonExistingId_ReturnsNotFound() throws Exception {
        when(studentService.findById(1L)).thenThrow(new com.platformbuilders.controleescolar.student.exception.StudentNotFoundException());

        mockMvc.perform(get("/api/students/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // ADDED: Print response for debugging
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void listStudents_ReturnsPageOfStudentDTOs() throws Exception {
        StudentDTO studentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();
        PageImpl<StudentDTO> pageResponse = new PageImpl<>(Collections.singletonList(studentDTO), Pageable.ofSize(10), 1);

        when(studentService.findAll(any(Pageable.class))).thenReturn(pageResponse);

        mockMvc.perform(get("/api/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void updateStudent_ExistingIdAndValidInput_ReturnsUpdatedStudentDTO() throws Exception {
        StudentDTO requestDTO = StudentDTO.builder()
                .firstName("UpdatedJohn")
                .lastName("Doe")
                .gender("MALE")
                .age(11)
                .level("MIDDLE_SCHOOL")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();
        StudentDTO responseDTO = StudentDTO.builder()
                .id(1L)
                .firstName("UpdatedJohn")
                .lastName("Doe")
                .gender("MALE")
                .age(11)
                .level("MIDDLE_SCHOOL")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        when(studentService.update(any(Long.class), any(StudentDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("UpdatedJohn"));
    }

    @Test
    void updateStudent_NonExistingId_ReturnsNotFound() throws Exception {
        StudentDTO requestDTO = StudentDTO.builder()
                .firstName("UpdatedJohn")
                .lastName("Doe")
                .gender("MALE")
                .age(11)
                .level("MIDDLE_SCHOOL")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        when(studentService.update(any(Long.class), any(StudentDTO.class)))
                .thenThrow(new com.platformbuilders.controleescolar.student.exception.StudentNotFoundException());

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void deleteStudent_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_NonExistingId_ReturnsNotFound() throws Exception {
        Mockito.doThrow(new com.platformbuilders.controleescolar.student.exception.StudentNotFoundException())
                .when(studentService).delete(1L);

        mockMvc.perform(delete("/api/students/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // ADDED: Print response for debugging
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }
}