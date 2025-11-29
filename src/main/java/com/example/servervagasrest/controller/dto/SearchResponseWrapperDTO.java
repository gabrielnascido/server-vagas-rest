package com.example.servervagasrest.controller.dto;

import java.util.List;

public record SearchResponseWrapperDTO(
        List<JobsResponseDTO> items
) {
}