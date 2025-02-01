package com.platformbuilders.controleescolar.student.api;

import com.platformbuilders.controleescolar.student.api.dto.StudentDTO;
import com.platformbuilders.controleescolar.student.service.StudentService;
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

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    @Value("${app.max-page-size:50}")
    private int maxPageSize;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @GetMapping("/{id}")
    public StudentDTO findStudent(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping
    public Page<StudentDTO> listStudents(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        int pageSize = Math.min(size, this.maxPageSize);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return studentService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO) {
        return studentService.update(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }
}