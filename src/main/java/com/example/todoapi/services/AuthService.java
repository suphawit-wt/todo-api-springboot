package com.example.todoapi.services;

import com.example.todoapi.models.request.LoginRequest;
import com.example.todoapi.models.request.RegisterRequest;

public interface AuthService {
    void Register(RegisterRequest req);
    String Login(LoginRequest req);
}