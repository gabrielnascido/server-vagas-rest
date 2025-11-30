package com.example.servervagasrest.controller.dto;

import com.example.servervagasrest.model.Job;

public record JobsResponseDTO(
        Long job_id,
        String title,
        String area,
        String description,
        String company,
        String city,
        String state,
        Float salary
        ) {

}