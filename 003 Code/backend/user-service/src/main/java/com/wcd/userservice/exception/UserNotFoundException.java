package com.wcd.userservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";
    private final HttpStatus httpStatus;

    public UserNotFoundException() {
        super(MESSAGE);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
