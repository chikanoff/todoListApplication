package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.exceptions.ItemNotFoundException;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(CreateTodoItemRequest item) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username).get();
        TodoItem todo = TodoItem.builder()
                                .name(item.getName())
                                .description(item.getDescription())
                                .date(item.getDate())
                                .build();
        todo.setUser(currentUser);
        todoItemRepository.save(todo);
    }

    @Override
    public void update(Long id, UpdateTodoItemRequest req) {
        Optional<TodoItem> i = todoItemRepository.findById(id);
        i.ifPresentOrElse(
                x -> {
                    x.setDescription(req.getDescription());
                    x.setDone(req.isDone());
                    x.setName(req.getName());
                    x.setDate(req.getDate());
                    todoItemRepository.save(x);
                },
                () -> {
                    throw new ItemNotFoundException("Item not found with id" + id);
                });
    }

    @Override
    public void changeStatus(Long id) {
        Optional<TodoItem> item = todoItemRepository.findById(id);
        // NEW
        // OLD
        if (item.isPresent()) {
            TodoItem existingItem = item.get();
            existingItem.setDone(!existingItem.isDone());
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        todoItemRepository.deleteById(id);
    }

    @Override
    public List<TodoItem> findAll() {
        return todoItemRepository.findAll();
    }

    @Override
    public TodoItem findById(Long id) {
        Optional<TodoItem> item = todoItemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }
}
