package com.zendesk.company.routeplanner.exception;


import org.springframework.http.HttpStatus;

/**
 * Custom class for BadRequestException.
 */
public class BadRequestException extends AbstractCustomException {
    private static final long serialVersionUID = 588460394602133350L;
    private static final String MESSAGE = "You are not allowed to perform this operation.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
        super(MESSAGE, STATUS);
    }

    public BadRequestException(String message) {
        super(message, STATUS);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, STATUS, cause);
    }
}