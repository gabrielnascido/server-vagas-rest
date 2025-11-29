package com.example.servervagasrest.controller.dto;

import java.util.List;

public record ApplicationsByUserWrapperDTO(

        List<ApplicationsByUserResponseDTO> items
) {
}
