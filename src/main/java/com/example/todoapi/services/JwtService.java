package com.example.todoapi.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String GenerateAccessToken(Long userId, String username);
    String extractTokenFromHeader();
    String GetUsername();
    Long GetUserId();
    boolean IsTokenValid(String token, UserDetails userDetails);
}