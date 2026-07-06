package com.alexmita.ordermanagementsystem.security;

import com.alexmita.ordermanagementsystem.security.dtos.AuthResponse;
import com.alexmita.ordermanagementsystem.security.dtos.LoginRequest;
import com.alexmita.ordermanagementsystem.security.dtos.SignupResponse;
import com.alexmita.ordermanagementsystem.security.dtos.SingupRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> register(@Valid @RequestBody SingupRequest request) {
        SignupResponse response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
