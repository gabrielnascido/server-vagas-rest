package com.example.servervagasrest.controller.dto;


public record ApplicationsByUserResponseDTO(

        Long job_id,

        String title,

        String area,

        String company,

        String description,

        String state,

        String city,

        Float salary,

        String contact,

        String feedback
) {
}
