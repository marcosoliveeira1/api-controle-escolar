package com.platformbuilders.controleescolar.student.service;

import com.platformbuilders.controleescolar.student.api.dto.StudentDTO;
import com.platformbuilders.controleescolar.student.domain.model.Student;
import com.platformbuilders.controleescolar.student.exception.StudentNotFoundException;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    @Test
    void create_ValidStudentDTO_ReturnsStudentDTO() {
        StudentDTO studentDTO = StudentDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .gender("MALE")
                .age(10)
                .level("ELEMENTARY")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();
        Student student = new Student();
        Student savedStudent = new Student();
        savedStudent.setId(1L);

        when(modelMapper.map(studentDTO, Student.class)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(savedStudent);
        when(modelMapper.map(savedStudent, StudentDTO.class)).thenReturn(StudentDTO.builder().id(1L).build());

        StudentDTO createdDTO = studentService.create(studentDTO);

        assertNotNull(createdDTO);
        assertEquals(1L, createdDTO.getId());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void findById_ExistingId_ReturnsStudentDTO() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        StudentDTO studentDTO = StudentDTO.builder().id(studentId).build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        StudentDTO foundDTO = studentService.findById(studentId);

        assertNotNull(foundDTO);
        assertEquals(studentId, foundDTO.getId());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void findById_NonExistingId_ThrowsStudentNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.findById(studentId));
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void findAll_ReturnsPageOfStudentDTOs() {
        Pageable pageable = Pageable.unpaged();
        Student student = new Student();
        StudentDTO studentDTO = StudentDTO.builder().id(1L).build();
        Page<Student> studentPage = new PageImpl<>(Collections.singletonList(student), pageable, 1);

        when(studentRepository.findAll(pageable)).thenReturn(studentPage);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        Page<StudentDTO> dtoPage = studentService.findAll(pageable);

        assertNotNull(dtoPage);
        assertFalse(dtoPage.isEmpty());
        assertEquals(1, dtoPage.getContent().size());
        verify(studentRepository, times(1)).findAll(pageable);
    }

    @Test
    void update_NonExistingId_ThrowsStudentNotFoundException() {
        Long studentId = 1L;
        StudentDTO updateDTO = StudentDTO.builder()
                .firstName("UpdatedName")
                .lastName("Doe")
                .gender("MALE")
                .age(11)
                .level("MIDDLE_SCHOOL")
                .guardianName("Jane Doe")
                .schoolId(1L)
                .build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.update(studentId, updateDTO));
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void delete_ExistingId_DeletesStudent() {
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(true);

        studentService.delete(studentId);

        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void delete_NonExistingId_ThrowsStudentNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> studentService.delete(studentId));
        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, never()).deleteById(any());
    }
}