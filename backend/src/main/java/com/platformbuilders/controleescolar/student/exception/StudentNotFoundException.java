package com.platformbuilders.controleescolar.student.exception;

import com.platformbuilders.controleescolar.exception.ResourceNotFoundException;

public class StudentNotFoundException extends ResourceNotFoundException {
    public StudentNotFoundException() {
        super("Estudante não encontrado");
    }
}