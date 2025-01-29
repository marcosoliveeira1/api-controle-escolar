package com.platformbuilders.controleescolar.service;

import com.platformbuilders.controleescolar.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.domain.model.School;
import com.platformbuilders.controleescolar.infra.repository.SchoolRepository;
import com.platformbuilders.controleescolar.school.exception.SchoolCnpjAlreadyExistsException;
import com.platformbuilders.controleescolar.school.exception.SchoolNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository, ModelMapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
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

    public List<SchoolDTO> findAll() {

        return schoolRepository.findAll().stream()
                .map(school -> modelMapper.map(school, SchoolDTO.class))
                .collect(Collectors.toList());
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

        schoolRepository.deleteById(id);
    }
}