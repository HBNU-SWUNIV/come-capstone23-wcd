package com.wcd.boardservice.exception;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public PostNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}