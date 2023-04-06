package com.fiuady.registrationApp.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long groupId) {
        super(String.format("Group with id '%s' not found", groupId));
    }
}
