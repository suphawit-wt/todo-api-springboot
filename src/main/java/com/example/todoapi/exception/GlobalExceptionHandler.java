package com.example.todoapi.exception;

import com.example.todoapi.exception.http.*;
import com.example.todoapi.models.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> UnauthorizedException(UnauthorizedException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> ForbiddenException(ForbiddenException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundException(NotFoundException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> ConflictException(ConflictException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> InternalServerErrorException(InternalServerErrorException ex) {
        var responseMessage = new MessageResponse();
        responseMessage.setMessage(ex.getMessage());

        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}