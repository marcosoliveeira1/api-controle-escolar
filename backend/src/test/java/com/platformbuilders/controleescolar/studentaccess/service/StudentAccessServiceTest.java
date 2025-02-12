package com.platformbuilders.controleescolar.studentaccess.service;

import com.platformbuilders.controleescolar.student.domain.model.Student;
import com.platformbuilders.controleescolar.student.exception.StudentNotFoundException;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.domain.model.StudentAccess;
import com.platformbuilders.controleescolar.studentaccess.infra.repository.StudentAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentAccessServiceTest {

    @Mock
    private StudentAccessRepository accessRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentAccessService studentAccessService;

    @Test
    void registerEntry_ExistingStudentId_ReturnsStudentAccessDTO() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        StudentAccess savedAccess = new StudentAccess();
        savedAccess.setId(1L);
        savedAccess.setStudent(student);
        StudentAccessDTO accessDTO = new StudentAccessDTO();
        accessDTO.setStudentId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(accessRepository.save(Mockito.any(StudentAccess.class))).thenReturn(savedAccess);
        when(modelMapper.map(savedAccess, StudentAccessDTO.class)).thenReturn(accessDTO);

        StudentAccessDTO resultDTO = studentAccessService.registerEntry(studentId);

        assertNotNull(resultDTO);
        assertEquals(studentId, resultDTO.getStudentId());
        verify(studentRepository, times(1)).findById(studentId);
        verify(accessRepository, times(1)).save(Mockito.any(StudentAccess.class));
    }

    @Test
    void registerEntry_NonExistingStudentId_ThrowsStudentNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentAccessService.registerEntry(studentId));
        verify(studentRepository, times(1)).findById(studentId);
        verify(accessRepository, never()).save(any());
    }

    @Test
    void registerExit_ExistingStudentId_EntryExists_ReturnsStudentAccessDTO() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        StudentAccess existingAccess = new StudentAccess();
        existingAccess.setId(1L);
        existingAccess.setStudent(student);
        existingAccess.setEntryTime(LocalDateTime.now().minusHours(1));
        StudentAccess savedAccess = new StudentAccess();
        savedAccess.setId(1L);
        savedAccess.setStudent(student);
        savedAccess.setEntryTime(existingAccess.getEntryTime());
        savedAccess.setExitTime(LocalDateTime.now());
        StudentAccessDTO accessDTO = new StudentAccessDTO();
        accessDTO.setStudentId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(accessRepository.findByStudentId(studentId)).thenReturn(Optional.of(existingAccess));
        when(accessRepository.save(Mockito.any(StudentAccess.class))).thenReturn(savedAccess);
        when(modelMapper.map(savedAccess, StudentAccessDTO.class)).thenReturn(accessDTO);

        StudentAccessDTO resultDTO = studentAccessService.registerExit(studentId);

        assertNotNull(resultDTO);
        assertEquals(studentId, resultDTO.getStudentId());
        verify(studentRepository, times(1)).findById(studentId);
        verify(accessRepository, times(1)).findByStudentId(studentId);
        verify(accessRepository, times(1)).save(Mockito.any(StudentAccess.class));
    }

    @Test
    void registerExit_ExistingStudentId_NoEntryExists_CreatesNewEntryAndExit() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        StudentAccess savedAccess = new StudentAccess();
        savedAccess.setId(1L);
        savedAccess.setStudent(student);
        savedAccess.setEntryTime(LocalDateTime.now());
        savedAccess.setExitTime(LocalDateTime.now()); // Exit time will be set in service
        StudentAccessDTO accessDTO = new StudentAccessDTO();
        accessDTO.setStudentId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(accessRepository.findByStudentId(studentId)).thenReturn(Optional.empty());
        when(accessRepository.save(Mockito.any(StudentAccess.class))).thenReturn(savedAccess);
        when(modelMapper.map(savedAccess, StudentAccessDTO.class)).thenReturn(accessDTO);

        StudentAccessDTO resultDTO = studentAccessService.registerExit(studentId);

        assertNotNull(resultDTO);
        assertEquals(studentId, resultDTO.getStudentId());
        verify(studentRepository, times(1)).findById(studentId);
        verify(accessRepository, times(1)).findByStudentId(studentId);
        verify(accessRepository, times(1)).save(Mockito.any(StudentAccess.class));
    }


    @Test
    void registerExit_NonExistingStudentId_ThrowsStudentNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentAccessService.registerExit(studentId));
        verify(studentRepository, times(1)).findById(studentId);
        verify(accessRepository, never()).findByStudentId(any());
        verify(accessRepository, never()).save(any());
    }
}