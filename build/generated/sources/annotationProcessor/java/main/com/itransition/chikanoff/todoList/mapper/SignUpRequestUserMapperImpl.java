package com.itransition.chikanoff.todoList.mapper;

import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.entity.User.UserBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-03T14:03:34+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.3.2.jar, environment: Java 15.0.1 (Oracle Corporation)"
)
public class SignUpRequestUserMapperImpl implements SignUpRequestUserMapper {

    @Override
    public User signUpRequestToUser(SignupRequest req) {
        if ( req == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.fullName( req.getFullName() );
        user.username( req.getUsername() );
        user.email( req.getEmail() );
        user.password( req.getPassword() );

        return user.build();
    }
}
