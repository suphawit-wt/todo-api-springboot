package com.example.todoapi.exception.http;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}