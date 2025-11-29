package com.example.servervagasrest.controller.dto;

public record LoginResponse(String token, long expiresIn) {}