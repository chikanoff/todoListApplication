package com.itransition.chikanoff.todoList.mapper;

import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {
    TodoItem requestToTodoItem(CreateTodoItemRequest req);
    void updateWithUpdateRequest(UpdateTodoItemRequest req, @MappingTarget TodoItem item);
}
