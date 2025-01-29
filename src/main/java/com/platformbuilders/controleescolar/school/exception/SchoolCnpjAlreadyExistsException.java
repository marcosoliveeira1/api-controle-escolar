package com.platformbuilders.controleescolar.school.exception;

public class SchoolCnpjAlreadyExistsException extends RuntimeException{
    public SchoolCnpjAlreadyExistsException() {
        super("Já existe uma escola com esse CNPJ");
    }
}
