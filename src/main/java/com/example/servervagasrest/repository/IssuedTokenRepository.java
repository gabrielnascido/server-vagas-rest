package com.example.servervagasrest.repository;

import com.example.servervagasrest.model.IssuedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssuedTokenRepository extends JpaRepository<IssuedToken, Long> {
    List<IssuedToken> findByActiveTrue();
    Optional<IssuedToken> findByToken(String token);
}
