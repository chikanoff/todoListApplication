package com.itransition.chikanoff.todoList.repository;

import com.itransition.chikanoff.todoList.beans.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
