package com.wcd.boardservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedCommentEditException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UnauthorizedCommentEditException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    // Additional constructors or methods
}