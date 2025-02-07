package com.platformbuilders.controleescolar.student.service;

import com.platformbuilders.controleescolar.student.api.dto.StudentDTO;
import com.platformbuilders.controleescolar.student.domain.model.Student;
import com.platformbuilders.controleescolar.student.exception.StudentNotFoundException;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public StudentDTO create(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDTO.class);
    }

    public StudentDTO findById(Long id) {
        log.debug("Finding student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student not found with ID: {}", id);
                    return new StudentNotFoundException();
                });
        return modelMapper.map(student, StudentDTO.class);
    }

    public Page<StudentDTO> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(student -> modelMapper.map(student, StudentDTO.class));
    }

    public StudentDTO update(Long id, StudentDTO updatedStudentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);

        modelMapper.map(updatedStudentDTO, existingStudent);
        existingStudent.setId(id);

        Student updatedStudent = studentRepository.save(existingStudent);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(id);
    }
}