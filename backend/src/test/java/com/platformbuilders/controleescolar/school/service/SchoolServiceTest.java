package com.platformbuilders.controleescolar.school.service;

import com.platformbuilders.controleescolar.school.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.school.domain.model.School;
import com.platformbuilders.controleescolar.school.exception.SchoolCnpjAlreadyExistsException;
import com.platformbuilders.controleescolar.school.exception.SchoolHasStudentsException;
import com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException;
import com.platformbuilders.controleescolar.school.infra.repository.SchoolRepository;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SchoolService schoolService;

    @Test
    void create_ValidSchoolDTO_ReturnsSchoolDTO() {
        SchoolDTO schoolDTO = SchoolDTO.builder()
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();
        School school = new School();
        School savedSchool = new School();
        savedSchool.setId(1L);

        when(schoolRepository.existsByCnpj(schoolDTO.getCnpj())).thenReturn(false);
        when(modelMapper.map(schoolDTO, School.class)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(savedSchool);
        when(modelMapper.map(savedSchool, SchoolDTO.class)).thenReturn(SchoolDTO.builder().id(1L).build());

        SchoolDTO createdDTO = schoolService.create(schoolDTO);

        assertNotNull(createdDTO);
        assertEquals(1L, createdDTO.getId());
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    void create_ExistingCnpj_ThrowsSchoolCnpjAlreadyExistsException() {
        SchoolDTO schoolDTO = SchoolDTO.builder()
                .name("School Name")
                .cnpj("76.662.830/0001-46")
                .address("School Address")
                .phone("1234-5678")
                .studentsPerClassroom(20)
                .build();

        when(schoolRepository.existsByCnpj(schoolDTO.getCnpj())).thenReturn(true);

        assertThrows(SchoolCnpjAlreadyExistsException.class, () -> schoolService.create(schoolDTO));
        verify(schoolRepository, times(1)).existsByCnpj(schoolDTO.getCnpj());
        verify(schoolRepository, never()).save(any());
    }

    @Test
    void findById_ExistingId_ReturnsSchoolDTO() {
        Long schoolId = 1L;
        School school = new School();
        school.setId(schoolId);
        SchoolDTO schoolDTO = SchoolDTO.builder().id(schoolId).build();

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(school));
        when(modelMapper.map(school, SchoolDTO.class)).thenReturn(schoolDTO);

        SchoolDTO foundDTO = schoolService.findById(schoolId);

        assertNotNull(foundDTO);
        assertEquals(schoolId, foundDTO.getId());
        verify(schoolRepository, times(1)).findById(schoolId);
    }

    @Test
    void findById_NonExistingId_ThrowsSchoolNotFoundException() {
        Long schoolId = 1L;
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());

        assertThrows(SchoolNotFoundException.class, () -> schoolService.findById(schoolId));
        verify(schoolRepository, times(1)).findById(schoolId);
    }

    @Test
    void findAll_ReturnsPageOfSchoolDTOs() {
        Pageable pageable = Pageable.unpaged();
        School school = new School();
        SchoolDTO schoolDTO = SchoolDTO.builder().id(1L).build();
        Page<School> schoolPage = new PageImpl<>(Collections.singletonList(school), pageable, 1);

        when(schoolRepository.findAll(pageable)).thenReturn(schoolPage);
        when(modelMapper.map(school, SchoolDTO.class)).thenReturn(schoolDTO);

        Page<SchoolDTO> dtoPage = schoolService.findAll(pageable);

        assertNotNull(dtoPage);
        assertFalse(dtoPage.isEmpty());
        assertEquals(1, dtoPage.getContent().size());
        verify(schoolRepository, times(1)).findAll(pageable);
    }

    @Test
    void update_NonExistingId_ThrowsSchoolNotFoundException() {
        Long schoolId = 1L;
        SchoolDTO updateDTO = SchoolDTO.builder()
                .name("Updated School Name")
                .cnpj("76.662.830/0001-46")
                .address("Updated School Address")
                .phone("1234-5678")
                .studentsPerClassroom(25)
                .build();

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());

        assertThrows(SchoolNotFoundException.class, () -> schoolService.update(schoolId, updateDTO));
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolRepository, never()).save(any());
    }

    @Test
    void update_ExistingCnpjOnUpdate_ThrowsSchoolCnpjAlreadyExistsException() {
        Long schoolId = 1L;
        SchoolDTO updateDTO = SchoolDTO.builder()
                .name("Updated School Name")
                .cnpj("99.999.999/0001-99") // Different CNPJ that exists
                .address("Updated School Address")
                .phone("1234-5678")
                .studentsPerClassroom(25)
                .build();
        School existingSchool = new School();
        existingSchool.setId(schoolId);
        existingSchool.setCnpj("76.662.830/0001-46"); // Original CNPJ

        when(schoolRepository.findById(schoolId)).thenReturn(Optional.of(existingSchool));
        when(schoolRepository.existsByCnpj(updateDTO.getCnpj())).thenReturn(true);

        assertThrows(SchoolCnpjAlreadyExistsException.class, () -> schoolService.update(schoolId, updateDTO));
        verify(schoolRepository, times(1)).findById(schoolId);
        verify(schoolRepository, times(1)).existsByCnpj(updateDTO.getCnpj());
        verify(schoolRepository, never()).save(any());
    }

    @Test
    void delete_ExistingId_NoStudents_DeletesSchool() {
        Long schoolId = 1L;
        when(schoolRepository.existsById(schoolId)).thenReturn(true);
        when(studentRepository.existsBySchoolId(schoolId)).thenReturn(false);

        schoolService.delete(schoolId);

        verify(schoolRepository, times(1)).existsById(schoolId);
        verify(studentRepository, times(1)).existsBySchoolId(schoolId);
        verify(schoolRepository, times(1)).deleteById(schoolId);
    }

    @Test
    void delete_ExistingId_HasStudents_ThrowsSchoolHasStudentsException() {
        Long schoolId = 1L;
        when(schoolRepository.existsById(schoolId)).thenReturn(true);
        when(studentRepository.existsBySchoolId(schoolId)).thenReturn(true);

        assertThrows(SchoolHasStudentsException.class, () -> schoolService.delete(schoolId));
        verify(schoolRepository, times(1)).existsById(schoolId);
        verify(studentRepository, times(1)).existsBySchoolId(schoolId);
        verify(schoolRepository, never()).deleteById(any());
    }

    @Test
    void delete_NonExistingId_ThrowsSchoolNotFoundException() {
        Long schoolId = 1L;
        when(schoolRepository.existsById(schoolId)).thenReturn(false);

        assertThrows(SchoolNotFoundException.class, () -> schoolService.delete(schoolId));
        verify(schoolRepository, times(1)).existsById(schoolId);
        verify(studentRepository, never()).existsBySchoolId(any());
        verify(schoolRepository, never()).deleteById(any());
    }
}