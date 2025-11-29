// java
package com.example.servervagasrest.service;

import com.example.servervagasrest.model.IssuedToken;
import com.example.servervagasrest.model.RevokedToken;
import com.example.servervagasrest.model.User;
import com.example.servervagasrest.repository.IssuedTokenRepository;
import com.example.servervagasrest.repository.RevokedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    private final RevokedTokenRepository revokedTokenRepository;
    private final IssuedTokenRepository issuedTokenRepository;

    @Value("${api.jwt.secret}")
    private String jwtSecret;

    @Value("${api.jwt.expiration-ms}")
    private long jwtExpirationMs;

    public TokenService(RevokedTokenRepository revokedTokenRepository, IssuedTokenRepository issuedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.issuedTokenRepository = issuedTokenRepository;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        SecretKey key = this.getSigningKey();

        String token = Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();

        IssuedToken issued = new IssuedToken(
                token,
                user.getId(),
                user.getUsername(),
                now.toInstant(),
                expiryDate.toInstant()
        );
        issuedTokenRepository.save(issued);

        return token;
    }

    public String getUsernameFromToken(String token) {
        SecretKey key = this.getSigningKey();
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.get("username", String.class);
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = this.getSigningKey();
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception  e) {
            return false;
        }
    }

    @Transactional
    public void addToBlacklist(String token) {
        Instant expiryDate = getExpiryDate(token);

        RevokedToken revokedToken = new RevokedToken(token, expiryDate);
        revokedTokenRepository.save(revokedToken);

        issuedTokenRepository.findByToken(token).ifPresent(issued -> {
            issued.setActive(false);
            issuedTokenRepository.save(issued);
        });
    }

    public boolean isBlacklisted(String token) {
        return revokedTokenRepository.existsByToken(token);
    }

    private Instant getExpiryDate(String token) {
        SecretKey key = this.getSigningKey();

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().toInstant();

        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration().toInstant();

        } catch (SignatureException e) {
            throw new RuntimeException("Falha na Assinatura JWT, não é possível adicionar à blacklist.", e);

        } catch (Exception e) {
            throw new RuntimeException("Token malformado/inválido durante o logout.", e);
        }
    }
}
