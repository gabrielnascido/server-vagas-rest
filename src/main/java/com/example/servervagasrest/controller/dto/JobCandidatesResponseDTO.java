package com.example.servervagasrest.controller.dto;

public record JobCandidatesResponseDTO(

        Long user_id,

        String name,

        String email,

        String phone,

        String education,

        String experience
) {
    public JobCandidatesResponseDTO {

    }
}
