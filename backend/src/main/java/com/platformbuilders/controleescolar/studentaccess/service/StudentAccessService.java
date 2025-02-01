package com.platformbuilders.controleescolar.studentaccess.service;

import com.platformbuilders.controleescolar.school.infra.repository.SchoolRepository;
import com.platformbuilders.controleescolar.student.domain.model.Student;
import com.platformbuilders.controleescolar.student.exception.StudentNotFoundException;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.domain.model.StudentAccess;
import com.platformbuilders.controleescolar.studentaccess.infra.repository.StudentAccessRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            SchoolRepository schoolRepository,
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
                    return newAccess;
                });

        access.setExitTime(LocalDateTime.now());

        StudentAccess savedAccess = accessRepository.save(access);
        return modelMapper.map(savedAccess, StudentAccessDTO.class);
    }
}
