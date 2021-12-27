package com.itransition.chikanoff.todoList.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
