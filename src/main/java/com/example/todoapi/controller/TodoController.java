package com.example.todoapi.controller;

import com.example.todoapi.models.Todo;
import com.example.todoapi.models.response.MessageResponse;
import com.example.todoapi.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/todo", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping()
    public ResponseEntity<List<Todo>> GetAll() {
        List<Todo> todos = todoService.GetAll();

        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetById(
            @PathVariable Long id
    ) {
        Todo todo = todoService.GetById(id);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> Create(
            @Valid @RequestBody Todo req
    ){
        todoService.Create(req);

        var responseMessage = new MessageResponse();
        responseMessage.setMessage("Created Todo Successfully!");

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> Update(
            @PathVariable Long id,
            @RequestBody Todo req
    ){
        todoService.Update(id, req);

        var responseMessage = new MessageResponse();
        responseMessage.setMessage("Updated Todo Successfully!");

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(
            @PathVariable Long id
    ){
        todoService.Delete(id);

        var responseMessage = new MessageResponse();
        responseMessage.setMessage("Deleted Todo Successfully!");

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}