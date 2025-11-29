package com.example.servervagasrest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{error.required}")
    @Size(min = 3, max = 150, message = "{error.invalid_length}")
    private String title;

    @NotNull(message = "{error.required}")
    private Area area;

    @NotBlank(message = "{error.required}")
    @Size(min = 10, max = 5000, message = "{error.invalid_length}")
    private String description;

    @NotBlank(message = "{error.required}")
    private String state;

    @NotBlank(message = "{error.required}")
    private String city;

    @DecimalMin(value = "0.00", inclusive = false, message = "O salário deve ser maior que R$ 0,00.")
    private Float salary;

    private Long companyUserId;

}
