package com.example.servervagasrest.controller.dto;

import java.util.List;

public record JobCandidatesWrapperResponseDTO(
        List<JobCandidatesResponseDTO> items
) {
}
