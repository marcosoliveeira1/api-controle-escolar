package com.platformbuilders.controleescolar.school.exception;

import com.platformbuilders.controleescolar.exception.ResourceAlreadyExistsException;

public class SchoolCnpjAlreadyExistsException extends ResourceAlreadyExistsException {
    public SchoolCnpjAlreadyExistsException() {
        super("Já existe uma escola com esse CNPJ");
    }
}