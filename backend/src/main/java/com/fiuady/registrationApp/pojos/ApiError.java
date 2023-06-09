package com.fiuady.registrationApp.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ApiError {
    private String message;
    private ZonedDateTime timestamp;

    public ApiError(RuntimeException ex) {
        timestamp = ZonedDateTime.now();
        message = ex.getMessage();
    }

    public ApiError() {
        timestamp = ZonedDateTime.now();
    }
}
