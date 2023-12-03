package com.example.todoapi.controller;

import com.example.todoapi.models.request.LoginRequest;
import com.example.todoapi.models.request.RegisterRequest;
import com.example.todoapi.models.response.LoginResponse;
import com.example.todoapi.models.response.MessageResponse;
import com.example.todoapi.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> Register(
            @Valid @RequestBody RegisterRequest req
    ) {
        authService.Register(req);

        var responseMessage = new MessageResponse();
        responseMessage.setMessage("Register Successfully!");

        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(
            @Valid @RequestBody LoginRequest req
    ) {
        var accessToken = authService.Login(req);

        var loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}