package com.platformbuilders.controleescolar.school.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "schools")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo 'name' é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O campo 'cpnj' é obrigatório")
    @Column(nullable = false, unique = true)
    @CNPJ
    private String cnpj;

    @NotBlank(message = "O campo 'address' é obrigatório")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "O campo 'phone' é obrigatório")
    @Column(nullable = false)
    private String phone;

    @NotNull(message = "O campo 'studentsPerClassroom' é obrigatório")
    @Min(value = 1, message = "O campo 'studentsPerClassroom' deve ser maior que zero")
    @Column(name = "students_per_classroom", nullable = false)
    private Integer studentsPerClassroom;
}
