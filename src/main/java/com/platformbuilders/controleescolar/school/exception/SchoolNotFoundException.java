package com.platformbuilders.controleescolar.school.exception;

public class SchoolNotFoundException extends RuntimeException{
    public SchoolNotFoundException() {
        super("Escola não encontrada");
    }
}
