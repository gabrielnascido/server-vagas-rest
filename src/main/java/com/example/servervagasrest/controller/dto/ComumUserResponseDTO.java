package com.example.servervagasrest.controller.dto;

import com.example.servervagasrest.model.ComumUser;

public record ComumUserResponseDTO(
        String name,
        String username,
        String email,
        String phone,
        String experience,
        String education
) {

    public static ComumUserResponseDTO fromEntity(ComumUser user) {
        return new ComumUserResponseDTO(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getExperience(),
                user.getEducation()
        );
    }
}