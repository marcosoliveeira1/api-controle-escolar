package com.platformbuilders.controleescolar.exception.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldError> errors;

    public ValidationErrorResponse(int status, String code, String message,
                                   LocalDateTime timestamp, List<FieldError> errors) {
        super(status, code, message, timestamp);
        this.errors = errors;
    }
}