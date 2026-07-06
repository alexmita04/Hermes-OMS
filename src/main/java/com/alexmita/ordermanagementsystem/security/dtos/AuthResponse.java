package com.alexmita.ordermanagementsystem.security.dtos;

public record AuthResponse(
        String token,
        String username
) {}