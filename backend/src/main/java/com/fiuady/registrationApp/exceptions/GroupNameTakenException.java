package com.fiuady.registrationApp.exceptions;

public class GroupNameTakenException extends RuntimeException {
    public GroupNameTakenException(String groupName) {
        super(String.format("A group with the name '%s' already exists", groupName));
    }
}
