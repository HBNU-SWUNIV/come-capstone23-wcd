package com.wcd.clubservice.exception;

import org.springframework.http.HttpStatus;

public class ClubMemberNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ClubMemberNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}