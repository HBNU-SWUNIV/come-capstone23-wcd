package com.wcd.clubservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClubNameAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(ClubNameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<String> handleClubNotFoundException(ClubNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyJoinedClubException.class)
    public ResponseEntity<String> handleUserAlreadyJoinedClubException(UserAlreadyJoinedClubException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(ClubMemberNotFoundException.class)
    public ResponseEntity<String> handleClubMemberNotFoundException(ClubMemberNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}
