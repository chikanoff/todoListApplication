package com.itransition.chikanoff.todoList;

import com.itransition.chikanoff.todoList.beans.TodoItem;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Getter
public abstract class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createTestUser() {
        return userRepository.saveAndFlush(new User(
                "testFullName",
                "test",
                "testEmail@gmail.com",
                encoder.encode("password")
        ));
    }

    public TodoItem createTestTodoItem() {
        final int year = 2021;
        final int day = 21;
        User user = createTestUser();
        TodoItem item = new TodoItem("name", "description", new Date(year, Calendar.DECEMBER, day));
        item.setUser(user);
        return todoItemRepository.saveAndFlush(item);
    }

    @AfterEach
    public void resetDb() {
        todoItemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
