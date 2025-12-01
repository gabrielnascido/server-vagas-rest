package com.example.servervagasrest.controller.dto;

public record ClientErrorDTO(
        String message
) {
    public String getMessage() {
        return message;
    }
}
