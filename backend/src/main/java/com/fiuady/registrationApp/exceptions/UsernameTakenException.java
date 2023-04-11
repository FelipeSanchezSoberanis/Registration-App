package com.fiuady.registrationApp.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super(String.format("A user with the username '%s' already exists", username));
    }
}
