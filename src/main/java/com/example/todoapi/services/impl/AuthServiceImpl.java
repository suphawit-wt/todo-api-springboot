package com.example.todoapi.services.impl;

import com.example.todoapi.exception.http.ConflictException;
import com.example.todoapi.exception.http.UnauthorizedException;
import com.example.todoapi.models.*;
import com.example.todoapi.models.request.LoginRequest;
import com.example.todoapi.models.request.RegisterRequest;
import com.example.todoapi.repository.UserRepository;
import com.example.todoapi.services.AuthService;
import com.example.todoapi.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void Register(RegisterRequest req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();

        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new ConflictException("Username and Email must be unique.");
        }
    }

    @Override
    public String Login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        User user = userRepository.findByUsername(req.getUsername()).orElseThrow(
                () -> new UnauthorizedException("Username or Password is invalid.")
        );

        return jwtService.GenerateAccessToken(user.getId(), user.getUsername());
    }
}