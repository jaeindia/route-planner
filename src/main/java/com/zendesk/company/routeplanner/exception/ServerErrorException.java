package com.zendesk.company.routeplanner.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends AbstractCustomException {
    private static final long serialVersionUID = 588460394602133353L;
    private static final String MESSAGE = "Internal server error.";
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public ServerErrorException() {
        super(MESSAGE, STATUS);
    }

    public ServerErrorException(String message) {
        super(message, STATUS);
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, STATUS, cause);
    }
}