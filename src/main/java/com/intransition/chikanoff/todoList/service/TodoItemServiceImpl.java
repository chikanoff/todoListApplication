package com.intransition.chikanoff.todoList.service;

import com.intransition.chikanoff.todoList.beans.TodoItem;
import com.intransition.chikanoff.todoList.beans.User;
import com.intransition.chikanoff.todoList.exceptions.ItemNotFoundException;
import com.intransition.chikanoff.todoList.payloads.request.TodoItemRequest;
import com.intransition.chikanoff.todoList.repository.TodoItemRepository;
import com.intransition.chikanoff.todoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemServiceImpl implements TodoItemService{

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(TodoItemRequest item) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userRepository.findByUsername(username).isPresent()){
            User currentUser = userRepository.findByUsername(username).get();
            TodoItem todo = new TodoItem(item.getName(), item.getDescription(), item.getDate());
            todo.setUser(currentUser);
            todoItemRepository.save(todo);
        }
    }

    @Override
    public void update(Long id, TodoItemRequest item) {
        if(todoItemRepository.findById(id).isPresent()){
            TodoItem existingItem = todoItemRepository.getById(id);

            existingItem.setDescription(item.getDescription());
            existingItem.setName(item.getName());
            existingItem.setDate(item.getDate());

            todoItemRepository.save(existingItem);
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public void changeStatus(Long id) {
        if(todoItemRepository.findById(id).isPresent()) {
            TodoItem item = todoItemRepository.getById(id);
            item.setDone(!item.isDone());
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        if(todoItemRepository.findById(id).isPresent()) {
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
        if(todoItemRepository.findById(id).isPresent()) {
            return todoItemRepository.getById(id);
        } else {
            throw new ItemNotFoundException("Item not found with id " + id);
        }
    }
}
