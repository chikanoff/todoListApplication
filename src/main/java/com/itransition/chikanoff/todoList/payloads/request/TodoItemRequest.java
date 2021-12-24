package com.itransition.chikanoff.todoList.payloads.request;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TodoItemRequest {
    private String name;
    private String description;
    private Date date;
}
