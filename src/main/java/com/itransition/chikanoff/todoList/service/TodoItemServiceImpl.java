package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.exceptions.ItemNotFoundException;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(CreateTodoItemRequest item, String username) {
        if (userRepository.existsByUsername(username)) {
            User currentUser = userRepository.findByUsername(username).get();
            TodoItem todo = TodoItem.builder()
                                    .name(item.getName())
                                    .description(item.getDescription())
                                    .date(item.getDate())
                                    .build();
            todo.setUser(currentUser);
            todoItemRepository.save(todo);
        }
    }

    @Override
    public void update(Long id, UpdateTodoItemRequest item) {
        if (todoItemRepository.findById(id).isPresent()) {
            TodoItem existingItem = todoItemRepository.getById(id);

            existingItem.setDescription(item.getDescription());
            existingItem.setName(item.getName());
            existingItem.setDate(item.getDate());
            existingItem.setDone(item.isDone());

            todoItemRepository.save(existingItem);
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public void changeStatus(Long id) {
        if (todoItemRepository.findById(id).isPresent()) {
            TodoItem item = todoItemRepository.getById(id);
            item.setDone(!item.isDone());
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        if (todoItemRepository.findById(id).isPresent()) {
            todoItemRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public List<TodoItem> get() {
        return todoItemRepository.findAll();
    }

    @Override
    public TodoItem get(Long id) {
        if (todoItemRepository.findById(id).isPresent()) {
            return todoItemRepository.getById(id);
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }
}
