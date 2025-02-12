package com.platformbuilders.controleescolar.studentaccess.api.controller;

import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.service.StudentAccessService;
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
@RequestMapping("/api/access")
@Validated
public class StudentAccessController {

    private final StudentAccessService accessService;

    @Value("${app.max-page-size:50}")
    private int maxPageSize;

    @Autowired
    public StudentAccessController(StudentAccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/entry/{studentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentAccessDTO registerEntry(@PathVariable Long studentId) {
        return accessService.registerEntry(studentId);
    }

    @PostMapping("/exit/{studentId}")
    public StudentAccessDTO registerExit(@PathVariable Long studentId) {
        return accessService.registerExit(studentId);
    }

    @GetMapping
    public Page<StudentAccessDTO> listStudentAccess(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        int pageSize = Math.min(size, maxPageSize);
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));
        return accessService.findAll(pageable);
    }
}