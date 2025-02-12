package com.platformbuilders.controleescolar.studentaccess.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAccessDTO {
    private Long id;
    private Long studentId;
    private String entryTime;
    private String exitTime;
    private String status;
}