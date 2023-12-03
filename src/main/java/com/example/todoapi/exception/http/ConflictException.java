package com.example.todoapi.exception.http;

public class ConflictException extends RuntimeException {
    public ConflictException(String errorMessage){
        super(errorMessage);
    }
}