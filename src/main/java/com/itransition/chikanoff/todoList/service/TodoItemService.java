package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.beans.TodoItem;
import com.itransition.chikanoff.todoList.payloads.request.TodoItemRequest;

import java.util.List;

public interface TodoItemService {
    void create(TodoItemRequest item, String username);
    void update(Long id, TodoItemRequest item);
    void changeStatus(Long id);
    void delete(Long id);
    List<TodoItem> get();
    TodoItem get(Long id);
}
