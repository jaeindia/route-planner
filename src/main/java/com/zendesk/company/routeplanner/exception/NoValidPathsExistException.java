package com.zendesk.company.routeplanner.exception;


import org.springframework.http.HttpStatus;

public class NoValidPathsExistException extends AbstractCustomException {
    private static final long serialVersionUID = 588460394602133351L;
    private static final String MESSAGE = "No valid paths exist.";
    private static final HttpStatus STATUS = HttpStatus.OK;

    public NoValidPathsExistException() {
        super(MESSAGE, STATUS);
    }

    public NoValidPathsExistException(String message) {
        super(message, STATUS);
    }

    public NoValidPathsExistException(String message, Throwable cause) {
        super(message, STATUS, cause);
    }
}