package com.example.bc_spring_app.exception;

public class CustomerRequestInvalidError extends RuntimeException {
    public CustomerRequestInvalidError(String message) {
        super(message);
    }
}
