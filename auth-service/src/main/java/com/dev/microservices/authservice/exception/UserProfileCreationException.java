package com.dev.microservices.authservice.exception;

public class UserProfileCreationException extends RuntimeException {
    public UserProfileCreationException(String message) {
        super(message);
    }
}
