package com.intransition.chikanoff.todoList.service;

import com.intransition.chikanoff.todoList.beans.TodoItem;
import com.intransition.chikanoff.todoList.payloads.request.TodoItemRequest;

import java.util.List;

public interface TodoItemService {
    void create(TodoItemRequest item);
    void update(Long id, TodoItemRequest item);
    void changeStatus(Long id);
    void delete(Long id);
    List<TodoItem> get();
    TodoItem get(Long id);
}
