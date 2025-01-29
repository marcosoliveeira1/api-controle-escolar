package com.platformbuilders.controleescolar.api.controller;

import com.platformbuilders.controleescolar.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@Validated
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDTO createSchool(@RequestBody @Valid SchoolDTO schoolDTO) {
        return schoolService.create(schoolDTO);
    }

    @GetMapping("/{id}")
    public SchoolDTO findSchool(@PathVariable Long id) {
        return schoolService.findById(id);
    }

    @GetMapping
    public List<SchoolDTO> listSchools() {
        return schoolService.findAll();
    }

    @PutMapping("/{id}")
    public SchoolDTO updateSchool(@PathVariable Long id, @RequestBody @Valid SchoolDTO schoolDTO) {
        return schoolService.update(id, schoolDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable Long id) {
        schoolService.delete(id);
    }
}