package com.example.servervagasrest.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("comum")
public class ComumUser extends User{

    @Pattern(regexp = "^$|.{10,600}", message = "{error.invalid_format}")
    private String experience;

    @Pattern(regexp = "^$|.{10,600}", message = "{error.invalid_format}")
    private String education;

    @Override
    public String getRole() {
        return "comum";
    }
}
