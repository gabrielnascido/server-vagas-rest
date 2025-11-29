package com.example.servervagasrest.controller.dto;

import java.util.List;

public record ValidationErrorResponse(String message, String code, List<FieldErrorDetail> details) {}