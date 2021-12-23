package com.intransition.chikanoff.todoList.payloads.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String token;
}
