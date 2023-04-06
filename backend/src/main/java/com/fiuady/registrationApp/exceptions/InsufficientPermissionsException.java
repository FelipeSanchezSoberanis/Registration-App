package com.fiuady.registrationApp.exceptions;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String message) {
        super(String.format("Insufficient permissions: %s", message));
    }
}
