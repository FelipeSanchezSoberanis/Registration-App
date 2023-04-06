package com.fiuady.registrationApp.exceptionHandlers;

import com.fiuady.registrationApp.exceptions.InsufficientPermissionsException;
import com.fiuady.registrationApp.pojos.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InsufficientPermissionsExceptionsHandler {

    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<ApiError> handleInsufficientPermissionsException(
            InsufficientPermissionsException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
