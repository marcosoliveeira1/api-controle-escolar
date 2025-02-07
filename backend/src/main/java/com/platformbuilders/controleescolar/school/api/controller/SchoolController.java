package com.platformbuilders.controleescolar.school.api.controller;

import com.platformbuilders.controleescolar.school.api.dto.SchoolDTO;
import com.platformbuilders.controleescolar.school.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@Validated
public class SchoolController {

    private final SchoolService schoolService;

    @Value("${app.max-page-size:50}")
    private int maxPageSize;

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
    public Page<SchoolDTO> listSchools(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        int pageSize = Math.min(size, this.maxPageSize);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return schoolService.findAll(pageable);
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