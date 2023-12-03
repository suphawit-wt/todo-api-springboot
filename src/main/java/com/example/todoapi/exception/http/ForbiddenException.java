package com.example.todoapi.exception.http;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String errorMessage){
        super(errorMessage);
    }
}