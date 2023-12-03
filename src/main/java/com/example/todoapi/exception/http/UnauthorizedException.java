package com.example.todoapi.exception.http;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String errorMessage){
        super(errorMessage);
    }
}