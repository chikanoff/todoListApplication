package com.itransition.chikanoff.todoList.mapper;

import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.TodoItem.TodoItemBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-03T14:03:35+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.3.2.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
public class CreateRequestTodoItemMapperImpl implements CreateRequestTodoItemMapper {

    @Override
    public TodoItem requestToTodoItem(CreateTodoItemRequest req) {
        if ( req == null ) {
            return null;
        }

        TodoItemBuilder todoItem = TodoItem.builder();

        todoItem.name( req.getName() );
        todoItem.description( req.getDescription() );
        todoItem.date( req.getDate() );

        return todoItem.build();
    }
}
