package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.mapper.CreateRequestTodoItemMapper;
import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.exceptions.ItemNotFoundException;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import org.mapstruct.factory.Mappers;
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
        User currentUser = userRepository.findByUsername(username).orElseThrow(ItemNotFoundException::new);

        TodoItem todo = CreateRequestTodoItemMapper.INSTANCE.requestToTodoItem(item);
        todo.setUser(currentUser);
        todoItemRepository.save(todo);
    }

    @Override
    public void update(Long id, UpdateTodoItemRequest req) {
        TodoItem item = findOrThrow(id);
        item.setName(req.getName());
        item.setDescription(req.getDescription());
        item.setDate(req.getDate());
        todoItemRepository.save(item);
    }

    @Override
    public void changeStatus(Long id) {
        TodoItem item = findOrThrow(id);
        item.setDone(!item.isDone());
        todoItemRepository.save(item);
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
        return findOrThrow(id);
    }

    private TodoItem findOrThrow(Long id) {
        return todoItemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id " + id));
    }
}
