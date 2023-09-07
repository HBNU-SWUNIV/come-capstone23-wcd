package com.wcd.userservice.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Email address is already in use.";
    private final HttpStatus httpStatus;

    public EmailAlreadyExistsException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT; // 409 Conflict
    }

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
        this.httpStatus = HttpStatus.CONFLICT; // 409 Conflict
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

