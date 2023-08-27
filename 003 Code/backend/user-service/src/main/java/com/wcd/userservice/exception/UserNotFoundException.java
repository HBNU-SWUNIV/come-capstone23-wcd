package com.wcd.userservice.exception;

public class UserNotFoundException extends TopException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
