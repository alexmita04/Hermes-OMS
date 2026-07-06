package com.alexmita.ordermanagementsystem.security;

import com.alexmita.ordermanagementsystem.domain.user.User;
import com.alexmita.ordermanagementsystem.domain.user.UserRepository;
import com.alexmita.ordermanagementsystem.security.dtos.AuthResponse;
import com.alexmita.ordermanagementsystem.security.dtos.LoginRequest;
import com.alexmita.ordermanagementsystem.security.dtos.SignupResponse;
import com.alexmita.ordermanagementsystem.security.dtos.SingupRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public SignupResponse signup(SingupRequest request) {
        if(userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("this username is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        SignupResponse response = new SignupResponse("User successfully signed up!");

        return response;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String token = jwtService.generateToken(request.username());

        return new AuthResponse(token, request.username());
    }
}
