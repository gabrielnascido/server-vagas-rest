package com.example.servervagasrest.controller.dto;

import com.example.servervagasrest.model.CompanyUser;

public record CompanyUserResponseDTO(
        String name,
        String email,
        String phone,
        String street,
        String number,
        String city,
        String state,
        String business
) {

    public static CompanyUserResponseDTO fromEntity(CompanyUser user) {
        return new CompanyUserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getStreet(),
                user.getNumber(),
                user.getCity(),
                user.getState(),
                user.getBusiness()
        );
    }
}