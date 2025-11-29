package com.example.servervagasrest.controller.dto;

public record JobFilterDTO(
        String title,
        String area,
        String company,
        String state,
        String city,
        SalaryRangeDTO salary_range
) {
}
