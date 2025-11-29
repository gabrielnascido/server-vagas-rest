package com.example.servervagasrest.controller;

import com.example.servervagasrest.controller.dto.LoginRequest;
import com.example.servervagasrest.controller.dto.LoginResponse;
import com.example.servervagasrest.model.User;
import com.example.servervagasrest.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticatorController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticatorController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()
        );

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        var user = auth.getPrincipal();

        String token = tokenService.generateToken((User) user);

        return ResponseEntity.ok(new LoginResponse(token, 3600L));
    }

}
