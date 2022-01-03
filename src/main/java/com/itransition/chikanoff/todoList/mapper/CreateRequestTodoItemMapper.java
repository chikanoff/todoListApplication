package com.itransition.chikanoff.todoList.mapper;

import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateRequestTodoItemMapper {
    CreateRequestTodoItemMapper INSTANCE = Mappers.getMapper(CreateRequestTodoItemMapper.class);
    TodoItem requestToTodoItem(CreateTodoItemRequest req);
}
