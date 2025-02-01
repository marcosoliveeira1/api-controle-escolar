package com.platformbuilders.controleescolar.student.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;

    @NotBlank(message = "O campo 'first_name' é obrigatório")
    private String firstName;

    @NotBlank(message = "O campo 'last_name' é obrigatório")
    private String lastName;

    @NotNull(message = "O campo 'gender' é obrigatório")
    private String gender;

    @NotNull(message = "O campo 'age' é obrigatório")
    @Min(value = 0, message = "O campo 'age' deve ser maior ou igual a zero")
    private Integer age;

    @NotNull(message = "O campo 'level' é obrigatório")
    private String level;

    @NotBlank(message = "O campo 'guardian_name' é obrigatório")
    private String guardianName;

    @NotNull(message = "O campo 'schoolId' é obrigatório")
    private Long schoolId;
}