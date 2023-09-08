package com.wcd.clubservice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyJoinedClubException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UserAlreadyJoinedClubException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}