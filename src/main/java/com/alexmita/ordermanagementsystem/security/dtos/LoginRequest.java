package com.alexmita.ordermanagementsystem.security.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username is mandatory")
        String username,

        @NotBlank(message = "password is mandatory")
        String password
) {}