package com.itransition.chikanoff.todoList.mapper;

import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import com.itransition.chikanoff.todoList.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SignUpRequestUserMapper {
    SignUpRequestUserMapper INSTANCE = Mappers.getMapper(SignUpRequestUserMapper.class);
    User signUpRequestToUser(SignupRequest req);
}
