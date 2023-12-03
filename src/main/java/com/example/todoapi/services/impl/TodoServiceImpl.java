package com.example.todoapi.services.impl;

import com.example.todoapi.exception.http.ForbiddenException;
import com.example.todoapi.exception.http.NotFoundException;
import com.example.todoapi.models.Todo;
import com.example.todoapi.repository.TodoRepository;
import com.example.todoapi.services.JwtService;
import com.example.todoapi.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final JwtService jwtService;

    @Override
    public List<Todo> GetAll() {
        Long userId = jwtService.GetUserId();

        return todoRepository.findAllByUserId(userId);
    }

    @Override
    public Todo GetById(Long id){
        Long userId = jwtService.GetUserId();

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find Todo ID: " + id)
        );

        if (!Objects.equals(todo.getUserId(), userId)) {
            throw new ForbiddenException("You are not Owner of this Todo.");
        }

        return todo;
    }

    @Override
    public void Create(Todo req) {
        Long userId = jwtService.GetUserId();
        req.setUserId(userId);

        todoRepository.save(req);
    }

    @Override
    public void Update(Long id, Todo req) {
        Long userId = jwtService.GetUserId();

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find Todo ID: " + id)
        );

        if (!Objects.equals(todo.getUserId(), userId)) {
            throw new ForbiddenException("You are not Owner of this Todo.");
        }

        todo.setTitle(req.getTitle());
        todo.setDoneStatus(req.isDoneStatus());

        todoRepository.save(todo);
    }

    @Override
    public void Delete(Long id) {
        Long userId = jwtService.GetUserId();

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find Todo ID: " + id)
        );

        if (!Objects.equals(todo.getUserId(), userId)) {
            throw new ForbiddenException("You are not Owner of this Todo.");
        }

        todoRepository.delete(todo);
    }
}