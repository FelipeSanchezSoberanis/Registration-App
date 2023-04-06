package com.fiuady.registrationApp.exceptionHandlers;

import com.fiuady.registrationApp.exceptions.GroupNameTakenException;
import com.fiuady.registrationApp.exceptions.GroupNotFoundException;
import com.fiuady.registrationApp.pojos.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GroupExceptionsHandler {

    @ExceptionHandler(GroupNameTakenException.class)
    public ResponseEntity<ApiError> handleGroupNameTakenException(GroupNameTakenException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ApiError> handleGroupNotFoundException(GroupNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
