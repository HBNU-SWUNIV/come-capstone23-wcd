package com.wcd.userservice.exception;

public class FileStorageException extends TopException {

    private static final String MESSAGE = "파일 저장에 실패했습니다.";

    public FileStorageException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
