// java
package com.example.servervagasrest.controller;

import com.example.servervagasrest.RequestResponseLoggingFilter;
import com.example.servervagasrest.controller.dto.ClientErrorDTO;
import com.example.servervagasrest.repository.IssuedTokenRepository;
import com.example.servervagasrest.model.IssuedToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class AdminDashboardController {

    private final IssuedTokenRepository issuedTokenRepository;

    public AdminDashboardController(IssuedTokenRepository issuedTokenRepository) {
        this.issuedTokenRepository = issuedTokenRepository;
    }

    @GetMapping("/admin/dashboard/logs")
    public ResponseEntity<List<RequestResponseLoggingFilter.RequestResponseLog>> getLogs() {
        return ResponseEntity.ok(RequestResponseLoggingFilter.logs.stream().toList());
    }

    @GetMapping("/admin/dashboard/users")
    public ResponseEntity<List<Map<String, String>>> getLoggedUsers() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Map<String, String>> users = issuedTokenRepository.findByActiveTrue().stream()
                .map(IssuedToken::new)
                .map(issued -> Map.of(
                        "username", issued.getUsername(),
                        "issuedAt", issued.getIssuedAt() != null ? fmt.format(issued.getIssuedAt().atZone(java.time.ZoneId.systemDefault())) : "",
                        "expiresAt", issued.getExpiresAt() != null ? fmt.format(issued.getExpiresAt().atZone(java.time.ZoneId.systemDefault())) : ""
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PostMapping("/error")
    public ResponseEntity<Void> logClientError(@RequestBody ClientErrorDTO errorData) {

        System.out.println("--- [FALLBACK CLIENTE] REPORT DE ERRO ---");
        System.out.println("Mensagem recebida do App: " + errorData.getMessage());

        return ResponseEntity.ok().build();
    }
}
