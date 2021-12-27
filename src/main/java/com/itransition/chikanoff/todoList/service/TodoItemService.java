package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;

import java.util.List;

public interface TodoItemService {
    void create(CreateTodoItemRequest item, String username);

    void update(Long id, UpdateTodoItemRequest item);

    void changeStatus(Long id);

    void delete(Long id);

    List<TodoItem> get();

    TodoItem get(Long id);
}
