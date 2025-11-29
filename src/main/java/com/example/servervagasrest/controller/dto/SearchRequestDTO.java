package com.example.servervagasrest.controller.dto;

import java.util.List;

public record SearchRequestDTO(
        List<JobFilterDTO> filters
) {
}
