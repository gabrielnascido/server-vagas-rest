package com.example.servervagasrest.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FeedbackDTO(

    @NotNull
    Long user_id,

    @NotBlank
    @Pattern(regexp = ".{10,600}", message = "{error.invalid_length}")
    String message
) {
    public FeedbackDTO {
    }

    public String getMessage() {
        return message;
    }
}
