package com.fiuady.registrationApp.exceptions;

public class RegistrationEventNameTakenException extends RuntimeException {
    public RegistrationEventNameTakenException(String registrationEventName) {
        super(
                String.format(
                        "A registration event with the name '%s' already exists",
                        registrationEventName));
    }
}
