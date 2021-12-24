package com.itransition.chikanoff.todoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.TodoItem;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.payloads.request.LoginRequest;
import com.itransition.chikanoff.todoList.payloads.request.TodoItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoControllerTests extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getTokenFromAuthorization() throws Exception {
        final String username = "test";
        User user;
        if (getUserRepository().findByUsername(username).isPresent()) {
            user = getUserRepository().findByUsername(username).get();
        } else {
            user = createTestUser();
        }
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername(user.getUsername());
        loginReq.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString()
                .replace("{\"token\":\"", "")
                .replace("\"}", "");
    }

    @Test
    public void createTodoItemTest() throws Exception {
        TodoItemRequest req = new TodoItemRequest();
        req.setName("name");
        req.setDescription("desc");
        req.setDate(new Date());

        String token = getTokenFromAuthorization();

        mvc.perform(post("/api/todo/add")
                        .header("Authorization", "Bearer " + token)
                            .contentType("application/json")
                            .param("sendWelcomeMail", "true")
                            .content(objectMapper.writeValueAsString(req)))
                        .andExpect(status().isOk());
    }

    @Test
    public void deleteTodoItemTest() throws Exception {
        TodoItem item = createTestTodoItem();
        String token = getTokenFromAuthorization();

        mvc.perform(delete("/api/todo/delete/" + item.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTest() throws Exception {
        String token = getTokenFromAuthorization();

        mvc.perform(get("/api/todo/get")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneTest() throws Exception {
        TodoItem item = createTestTodoItem();
        String token = getTokenFromAuthorization();

        mvc.perform(get("/api/todo/get/" + item.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void changeStatusTest() throws Exception {
        TodoItem item = createTestTodoItem();
        String token = getTokenFromAuthorization();

        mvc.perform(post("/api/todo/changeStatus/" + item.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void editTodoItemTest() throws Exception {
        TodoItem item = createTestTodoItem();
        String token = getTokenFromAuthorization();

        TodoItemRequest req = new TodoItemRequest();
        req.setName("newName");
        req.setDescription("newDesc");
        req.setDate(new Date());

        mvc.perform(put("/api/todo/edit/" + item.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}
