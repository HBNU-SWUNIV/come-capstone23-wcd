package com.wcd.boardservice.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedEditException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UnAuthorizedEditException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}