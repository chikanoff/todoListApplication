package com.itransition.chikanoff.todoList.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String token;
}
