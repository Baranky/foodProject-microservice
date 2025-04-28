package com.example.gateway.dto;
import org.springframework.security.core.userdetails.UserDetails;

public record TokenValidationRequest(
        String token,
        UserDetails userDetails
        ) {
}
