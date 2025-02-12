package com.platformbuilders.controleescolar.school.service;

import com.platformbuilders.controleescolar.school.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.school.domain.model.School;
import com.platformbuilders.controleescolar.school.exception.SchoolCnpjAlreadyExistsException;
import com.platformbuilders.controleescolar.school.exception.SchoolHasStudentsException;
import com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException;
import com.platformbuilders.controleescolar.school.infra.repository.SchoolRepository;
import com.platformbuilders.controleescolar.student.infra.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    public SchoolDTO create(SchoolDTO schoolDTO) {
        if (schoolRepository.existsByCnpj(schoolDTO.getCnpj())) {
            throw new SchoolCnpjAlreadyExistsException();
        }

        School school = modelMapper.map(schoolDTO, School.class);
        School savedSchool = schoolRepository.save(school);

        return modelMapper.map(savedSchool, SchoolDTO.class);
    }

    public SchoolDTO findById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(SchoolNotFoundException::new);

        return modelMapper.map(school, SchoolDTO.class);
    }

    public Page<SchoolDTO> findAll(Pageable pageable) {
        return schoolRepository.findAll(pageable).map(school -> modelMapper.map(school, SchoolDTO.class));
    }

    public SchoolDTO update(Long id, SchoolDTO schoolDTO) {
        School existingSchool = schoolRepository.findById(id)
                .orElseThrow(SchoolNotFoundException::new);

        if (!existingSchool.getCnpj().equals(schoolDTO.getCnpj()) &&
                schoolRepository.existsByCnpj(schoolDTO.getCnpj())) {
            throw new SchoolCnpjAlreadyExistsException();
        }

        modelMapper.map(schoolDTO, existingSchool);
        existingSchool.setId(id);

        School updatedSchool = schoolRepository.save(existingSchool);

        return modelMapper.map(updatedSchool, SchoolDTO.class);
    }

    public void delete(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new SchoolNotFoundException();
        }

        if(studentRepository.existsBySchoolId(id)) {
            throw new SchoolHasStudentsException();
        }

        schoolRepository.deleteById(id);
    }
}