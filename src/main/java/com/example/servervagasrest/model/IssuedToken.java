package com.example.servervagasrest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "issued_tokens")
@Data
public class IssuedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 2000)
    private String token;

    private Long userId;

    private String username;

    private Instant issuedAt;

    private Instant expiresAt;

    private boolean active = true;

    public IssuedToken() {}

    public IssuedToken(String token, Long userId, String username, Instant issuedAt, Instant expiresAt) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.active = true;
    }

    public IssuedToken(IssuedToken issuedToken) {
        this.token = issuedToken.getToken();
        this.userId = issuedToken.getUserId();
        this.username = issuedToken.getUsername();
        this.issuedAt = issuedToken.getIssuedAt();
        this.expiresAt = issuedToken.getExpiresAt();
        this.active = issuedToken.isActive();

    }
}
