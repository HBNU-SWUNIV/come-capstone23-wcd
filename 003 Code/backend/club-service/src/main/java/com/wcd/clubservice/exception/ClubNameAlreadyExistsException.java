package com.wcd.clubservice.exception;

import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;

public class ClubNameAlreadyExistsException extends RuntimeException {
    private static final String message = "Club Name is already in use.";
    private final HttpStatus httpStatus;

    public ClubNameAlreadyExistsException() {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}