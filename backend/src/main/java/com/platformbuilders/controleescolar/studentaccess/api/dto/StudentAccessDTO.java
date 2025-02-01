package com.platformbuilders.controleescolar.studentaccess.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAccessDTO {
    private Long studentId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String status;
}