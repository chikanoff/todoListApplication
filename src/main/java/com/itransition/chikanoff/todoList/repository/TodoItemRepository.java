package com.itransition.chikanoff.todoList.repository;

import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
