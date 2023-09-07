package com.wcd.userservice.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationCodeIncorrectException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The authentication code provided is incorrect.";
    private final HttpStatus httpStatus;

    public AuthenticationCodeIncorrectException(String message) {
        super(message);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public AuthenticationCodeIncorrectException() {
        super(DEFAULT_MESSAGE);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
