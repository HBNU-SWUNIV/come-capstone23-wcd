package com.wcd.userservice.exception;

import org.springframework.http.HttpStatus;

public class PhoneNumberAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Phone Number is already in use.";
    private final HttpStatus httpStatus;

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT; // 409 Conflict
    }

    public PhoneNumberAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
        this.httpStatus = HttpStatus.CONFLICT; // 409 Conflict
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

