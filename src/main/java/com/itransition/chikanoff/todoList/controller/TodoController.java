package com.itransition.chikanoff.todoList.controller;

import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.service.TodoItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoItemServiceImpl todoService;

    @GetMapping("/{id}")
    public TodoItem get(@PathVariable("id") Long id) {
        return todoService.get(id);
    }

    @GetMapping("/")
    public List<TodoItem> get() {
        return todoService.get();
    }

    @PostMapping("/")
    public void add(@RequestBody CreateTodoItemRequest item) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        todoService.create(item, username);
    }

    @PatchMapping("/{id}")
    public void changeStatus(@PathVariable("id") Long id) {
        todoService.changeStatus(id);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable("id") Long id, @RequestBody UpdateTodoItemRequest item) {
        todoService.update(id, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        todoService.delete(id);
    }
}
