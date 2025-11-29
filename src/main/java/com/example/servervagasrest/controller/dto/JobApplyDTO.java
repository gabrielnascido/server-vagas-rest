package com.example.servervagasrest.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record JobApplyDTO(

        @NotBlank
        String name,

        String email,

        String phone,

        @NotBlank
        @Pattern(regexp = "^$|.{0,600}", message = "{error.invalid_length}")
        String education,

        @NotBlank
        @Pattern(regexp = "^$|.{0,600}", message = "{error.invalid_length}")
        String experience

) {
    public JobApplyDTO {
    }
}
