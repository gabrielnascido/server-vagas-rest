package com.example.servervagasrest.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyUserUpdateDTO(

        @Size(min = 4, max = 150, message = "{error.invalid_length}")
        String name,

        @Size(min = 3, max = 20, message = "{error.invalid_length}")
        String username,

        @Size(min = 3, max = 20, message = "{error.invalid_length}")
        String password,

        @Pattern(regexp = "^$|.{4,150}", message = "{error.invalid_format}")
        String business,

        @Pattern(regexp = "^$|.{3,150}", message = "{error.invalid_format}")
        String street,

        @Pattern(regexp = "^$|.{3,150}", message = "{error.invalid_format}")
        String city,

        @Pattern(regexp = "^[0-9]{1,8}$", message = "{error.invalid_format}")
        String number,

        @Pattern(regexp = "^$|.{2,100}", message = "{error.invalid_format}")
        String state,

        @Pattern(regexp = "^$|.{10,14}", message = "{error.invalid_length}")
        String phone,

        @Email(message = "{error.invalid_format}")
        String email

        ) {
}