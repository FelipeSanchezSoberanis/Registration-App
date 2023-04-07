package com.fiuady.registrationApp.exceptionHandlers;

import com.fiuady.registrationApp.exceptions.RegistrationEventNameTakenException;
import com.fiuady.registrationApp.pojos.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationEventsExceptionsHandler {

    @ExceptionHandler(RegistrationEventNameTakenException.class)
    public ResponseEntity<ApiError> RegistrationEventNameTakenExceptionHandler(
            RegistrationEventNameTakenException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
