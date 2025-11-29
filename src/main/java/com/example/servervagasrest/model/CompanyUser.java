package com.example.servervagasrest.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("company")
public class CompanyUser extends User {

    @NotBlank(message = "{error.required}")
    @Size(min = 4, max = 150, message = "{error.invalid_length}")
    private String business;

    @NotBlank(message = "{error.required}")
    @Size(min = 3, max = 150, message = "{error.invalid_length}")
    private String street;

    @NotBlank(message = "{error.required}")
    @Size(min = 3, max = 150, message = "{error.invalid_length}")
    private String city;

    @NotBlank(message = "{error.required}")
    @Pattern(regexp = "[0-9]{1,8}", message = "{error.invalid_format}")
    private String number;

    @NotBlank(message = "{error.required}")
    private String state;

    @Override
    public String getRole() {
        return "company";
    }
}
