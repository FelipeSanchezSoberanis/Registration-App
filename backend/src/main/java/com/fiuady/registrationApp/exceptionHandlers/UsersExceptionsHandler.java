package com.fiuady.registrationApp.exceptionHandlers;

import com.fiuady.registrationApp.exceptions.UsernameTakenException;
import com.fiuady.registrationApp.pojos.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsersExceptionsHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ApiError> handleUsernameTakenException(UsernameTakenException ex) {
        return new ResponseEntity<>(new ApiError(ex), HttpStatus.BAD_REQUEST);
    }
}
