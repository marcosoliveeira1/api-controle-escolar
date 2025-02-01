package com.platformbuilders.controleescolar.studentaccess.api.controller;

import com.platformbuilders.controleescolar.studentaccess.api.dto.StudentAccessDTO;
import com.platformbuilders.controleescolar.studentaccess.service.StudentAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access")
@Validated
public class StudentAccessController {

    private final StudentAccessService accessService;

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
}