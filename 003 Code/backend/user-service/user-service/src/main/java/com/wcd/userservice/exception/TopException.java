package com.wcd.userservice.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class TopException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public TopException(String message) {
        super(message);
    }

    public TopException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
