package com.example.servervagasrest.controller.dto;

import com.example.servervagasrest.model.Area;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record JobUpdateDTO(
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        Area area,

        @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
        String description,

        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "City must contain only letters and spaces")
        String city,

        @Pattern(regexp = "^[A-Za-z]{2}$", message = "State must be a valid two-letter abbreviation")
        String state,

        @DecimalMin(value = "0.00", inclusive = false, message = "O salário deve ser maior que R$ 0,00.")
        Float salary
) {
    public JobUpdateDTO {

    }
}