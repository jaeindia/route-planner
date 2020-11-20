package com.zendesk.company.routeplanner.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class.
 */
@Getter
public abstract class AbstractCustomException extends RuntimeException {
    private static final long serialVersionUID = 588460394602133350L;
    private static final String DEFAULT_MESSAGE = "Exceptions occurred. Please try again.";
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;
    private final String errorMessage;
    private final HttpStatus status;

    public AbstractCustomException() {
        super();
        errorMessage = DEFAULT_MESSAGE;
        status = DEFAULT_STATUS;
    }

    public AbstractCustomException(String message, HttpStatus status) {
        super(message);
        errorMessage = message;
        this.status = status;
    }

    public AbstractCustomException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        errorMessage = message;
        this.status = status;
    }
}
