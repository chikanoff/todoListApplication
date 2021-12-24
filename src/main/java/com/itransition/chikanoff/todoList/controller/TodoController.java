package com.itransition.chikanoff.todoList.controller;

import com.itransition.chikanoff.todoList.beans.TodoItem;
import com.itransition.chikanoff.todoList.payloads.request.TodoItemRequest;
import com.itransition.chikanoff.todoList.service.TodoItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/get/{id}")
    public TodoItem get(@PathVariable("id") Long id) {
        return todoService.get(id);
    }

    @GetMapping("/get")
    public List<TodoItem> get() {
        return todoService.get();
    }

    @PostMapping("/add")
    public void add(@RequestBody TodoItemRequest item) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        todoService.create(item, username);
    }

    @PostMapping("/changeStatus/{id}")
    public void changeStatus(@PathVariable("id") Long id) {
        todoService.changeStatus(id);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable("id") Long id, @RequestBody TodoItemRequest item) {
        todoService.update(id, item);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        todoService.delete(id);
    }
}
