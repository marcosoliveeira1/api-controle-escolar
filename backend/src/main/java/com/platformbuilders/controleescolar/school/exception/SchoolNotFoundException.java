package com.platformbuilders.controleescolar.school.exception;

import com.platformbuilders.controleescolar.exception.ResourceNotFoundException;

public class SchoolNotFoundException extends ResourceNotFoundException {
    public SchoolNotFoundException() {
        super("Escola não encontrada");
    }
}