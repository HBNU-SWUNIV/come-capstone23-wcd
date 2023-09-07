package com.wcd.userservice.exception;

import org.springframework.http.HttpStatus;

public class EmailSendingException extends RuntimeException {
    private final HttpStatus httpStatus;

    public EmailSendingException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 500 Internal Server Error
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}