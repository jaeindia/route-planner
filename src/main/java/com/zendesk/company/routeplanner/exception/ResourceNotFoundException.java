package com.zendesk.company.routeplanner.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom class for ResourceNotFoundException.
 */
public class ResourceNotFoundException extends AbstractCustomException {
    private static final long serialVersionUID = 588460394602133352L;
    private static final String MESSAGE = "Resource not found.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException() {
        super(MESSAGE, STATUS);
    }

    public ResourceNotFoundException(String message) {
        super(message, STATUS);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, STATUS, cause);
    }
}