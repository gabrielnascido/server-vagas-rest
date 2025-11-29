package com.example.servervagasrest.exception;

public class ImmutableFieldException extends RuntimeException {
    public ImmutableFieldException(String message) {
        super(message);
    }
}