package com.platformbuilders.controleescolar.student.domain.model;

import com.platformbuilders.controleescolar.school.domain.model.School;
import com.platformbuilders.controleescolar.student.domain.enums.Gender;
import com.platformbuilders.controleescolar.student.domain.enums.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo 'first_name' é obrigatório")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "O campo 'last_name' é obrigatório")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "O campo 'gender' é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull(message = "O campo 'age' é obrigatório")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Column(nullable = false)
    private Integer age;

    @NotNull(message = "O campo 'level' é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @NotBlank(message = "O campo 'guardian_name' é obrigatório")
    @Column(name = "guardian_name", nullable = false)
    private String guardianName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false)
    private School school;
}
