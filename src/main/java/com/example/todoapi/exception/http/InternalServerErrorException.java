package com.example.todoapi.exception.http;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String errorMessage){
        super(errorMessage);
    }
}