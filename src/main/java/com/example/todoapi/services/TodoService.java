package com.example.todoapi.services;

import com.example.todoapi.models.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> GetAll();
    Todo GetById(Long id);
    void Create(Todo req);
    void Update(Long id, Todo req);
    void Delete(Long id);
}