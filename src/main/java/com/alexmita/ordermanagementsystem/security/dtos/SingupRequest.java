package com.alexmita.ordermanagementsystem.security.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SingupRequest(
    @NotBlank(message = "username can't be empty")
    @Size(min = 4, max = 50, message = "username should have between 4 and 50 characters")
    String username,

    @NotBlank(message = "password can't be empty")
    @Size(min = 6, message = "password should have at least 6 characters")
    String password
){}
