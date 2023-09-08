package com.wcd.clubservice.exception;

import org.springframework.http.HttpStatus;

public class ClubNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ClubNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}