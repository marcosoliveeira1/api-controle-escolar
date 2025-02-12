package com.platformbuilders.controleescolar.studentaccess.service;

import com.platformbuilders.controleescolar.school.infra.repository.SchoolRepository;
import com.platformbuilders.controleescolar.student.domain.model.Student;
import com.platformbuilders.controleescolar.student.exception.StudentNotFoundException;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.domain.model.StudentAccess;
import com.platformbuilders.controleescolar.studentaccess.infra.repository.StudentAccessRepository;
import io.swagger.v3.core.util.Json;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class StudentAccessService {

    private final StudentAccessRepository accessRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentAccessService(
            StudentAccessRepository accessRepository,
            StudentRepository studentRepository,
            ModelMapper modelMapper) {
        this.accessRepository = accessRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public StudentAccessDTO registerEntry(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        StudentAccess access = new StudentAccess();
        access.setStudent(student);
        access.setEntryTime(LocalDateTime.now());
        log.info("Student {} entry registered at {}", studentId, LocalDateTime.now());

        StudentAccess savedAccess = accessRepository.save(access);
        return modelMapper.map(savedAccess, StudentAccessDTO.class);
    }

    @Transactional
    public StudentAccessDTO registerExit(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        StudentAccess access = accessRepository
                .findByStudentId(studentId)
                .orElseGet(() -> {
                    StudentAccess newAccess = new StudentAccess();
                    newAccess.setStudent(student);
                    newAccess.setEntryTime(LocalDateTime.now());
                    return newAccess;
                });

        access.setExitTime(LocalDateTime.now());
        log.info("Student {} exit registered at {}", studentId, LocalDateTime.now());
        StudentAccess savedAccess = accessRepository.save(access);
        return modelMapper.map(savedAccess, StudentAccessDTO.class);
    }

    public Page<StudentAccessDTO> findAll(Pageable pageable) {
        Page<StudentAccess> studentAccessPage = accessRepository.findAll(pageable);
        return studentAccessPage.map(access -> modelMapper.map(access, StudentAccessDTO.class));
    }
}