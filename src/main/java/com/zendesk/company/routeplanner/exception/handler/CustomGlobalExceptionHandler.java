package com.zendesk.company.routeplanner.exception.handler;

import com.zendesk.company.routeplanner.exception.AbstractCustomException;
import com.zendesk.company.routeplanner.constants.Consts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Custom class for all raised exceptions.
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LogManager.getLogger(CustomGlobalExceptionHandler.class);

    // Error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(Consts.TIMESTAMP, new Date());
        body.put(Consts.STATUS, status.value());
        //Get all fields errors
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put(Consts.MESSAGE, messages);
        body.put(Consts.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());

        log.info("MethodArgumentNotValidException {}", body);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(AbstractCustomException.class)
    public ResponseEntity<Object> customHandleNotFound(AbstractCustomException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(Consts.TIMESTAMP, new Date());
        body.put(Consts.STATUS, ex.getStatus().value());
        body.put(Consts.MESSAGE, Arrays.asList(ex.getMessage()));
        body.put(Consts.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());

        log.info("AbstractCustomException {}", body);

        return new ResponseEntity<>(body, ex.getStatus());
    }
}
