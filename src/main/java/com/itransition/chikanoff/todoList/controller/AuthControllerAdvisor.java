package com.itransition.chikanoff.todoList.controller;

import com.itransition.chikanoff.todoList.exceptions.DataExistException;
import com.itransition.chikanoff.todoList.payloads.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class AuthControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public MessageResponse handleEmailExistException(DataExistException ex) {
        return MessageResponse.builder()
                .message(ex.getMessage())
                .build();
    }
}
