package com.wcd.userservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<String> handlePhoneNumberAlreadyExists(PhoneNumberAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(AuthenticationCodeIncorrectException.class)
    public ResponseEntity<String> handleAuthenticationCodeIncorrectException(AuthenticationCodeIncorrectException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<String> handleEmailSendingException(EmailSendingException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
