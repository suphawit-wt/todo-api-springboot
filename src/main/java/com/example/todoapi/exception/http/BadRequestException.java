package com.example.todoapi.exception.http;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}