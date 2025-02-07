package com.platformbuilders.controleescolar.school.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolDTO {
    private Long id;

    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;

    @NotBlank(message = "O campo 'cpnj' é obrigatório")
    @CNPJ
    private String cnpj;

    @NotBlank(message = "O campo 'address' é obrigatório")
    private String address;

    @NotBlank(message = "O campo 'phone' é obrigatório")
    private String phone;

    @NotNull(message = "O campo 'studentsPerClassroom' é obrigatório")
    @Min(value = 1, message = "O campo 'studentsPerClassroom' deve ser maior que zero")
    private Integer studentsPerClassroom;
}