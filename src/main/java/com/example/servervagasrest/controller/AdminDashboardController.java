// java
package com.example.servervagasrest.controller;

import com.example.servervagasrest.RequestResponseLoggingFilter;
import com.example.servervagasrest.repository.IssuedTokenRepository;
import com.example.servervagasrest.model.IssuedToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    private final IssuedTokenRepository issuedTokenRepository;

    public AdminDashboardController(IssuedTokenRepository issuedTokenRepository) {
        this.issuedTokenRepository = issuedTokenRepository;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<RequestResponseLoggingFilter.RequestResponseLog>> getLogs() {
        return ResponseEntity.ok(RequestResponseLoggingFilter.logs.stream().toList());
    }

    @GetMapping("/users")
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
}
