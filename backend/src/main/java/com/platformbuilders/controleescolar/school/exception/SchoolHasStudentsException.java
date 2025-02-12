package com.platformbuilders.controleescolar.school.exception;

import com.platformbuilders.controleescolar.exception.BusinessRuleException;

public class SchoolHasStudentsException extends BusinessRuleException {
    public SchoolHasStudentsException() {
        super("A escola possui alunos cadastrados");
    }
}
