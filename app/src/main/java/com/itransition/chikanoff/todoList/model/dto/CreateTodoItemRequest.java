package com.itransition.chikanoff.todoList.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CreateTodoItemRequest {
    private String name;
    private String description;
    private Date date;
}
