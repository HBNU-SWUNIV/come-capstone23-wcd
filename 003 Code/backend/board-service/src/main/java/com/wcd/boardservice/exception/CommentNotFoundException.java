package com.wcd.boardservice.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CommentNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}