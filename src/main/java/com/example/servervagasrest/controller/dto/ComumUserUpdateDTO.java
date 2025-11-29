package com.example.servervagasrest.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComumUserUpdateDTO(
        @Email(message = "{error.invalid_format}")
        String email,

        @Size(min = 4, max = 50, message = "{error.invalid_length}")
        String name,

        @Size(min = 8, max = 20, message = "{error.invalid_length}")
        String password,

        @Pattern(regexp = "^$|.{10,14}", message = "{error.invalid_length}")
        String phone,

        @Pattern(regexp = "^$|.{10,600}", message = "{error.invalid_length}")
        String experience,

        @Pattern(regexp = "^$|.{10,600}", message = "{error.invalid_length}")
        String education,

        String username
) {
    public ComumUserUpdateDTO {
    }
}