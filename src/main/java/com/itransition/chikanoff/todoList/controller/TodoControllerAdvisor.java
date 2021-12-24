package com.itransition.chikanoff.todoList.controller;

import com.itransition.chikanoff.todoList.exceptions.ItemNotFoundException;
import com.itransition.chikanoff.todoList.payloads.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class TodoControllerAdvisor  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public MessageResponse handleEmailExistException(ItemNotFoundException ex) {
        return MessageResponse.builder()
                .message(ex.getMessage())
                .build();
    }
}
