package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.TodoItem;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.payloads.request.TodoItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class TodoServiceTest extends IntegrationTestBase {
    private final long id = 3L;
    @Autowired
    private TodoItemServiceImpl todoItemService;

    @Test
    public void getByIdReturnsException() {
        assertThatThrownBy(() -> todoItemService.get(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void editReturnsException() {
        assertThatThrownBy(() -> todoItemService.update(id, new TodoItemRequest())).hasMessage("Item not found with id " + id);
    }

    @Test
    public void changeStatusReturnsException() {
        assertThatThrownBy(() -> todoItemService.changeStatus(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void deleteReturnsException() {
        assertThatThrownBy(() -> todoItemService.delete(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void addTodoItemTest() {
        final int year = 2021;
        final int day = 21;
        User user = createTestUser();
        TodoItemRequest itemRequest = new TodoItemRequest();
        itemRequest.setName("name");
        itemRequest.setDescription("desc");
        itemRequest.setDate(new Date(year, Calendar.DECEMBER, day));
        todoItemService.create(itemRequest, user.getUsername());

        List<TodoItem> items = getTodoItemRepository().findAll();
        TodoItem item = items.get(items.size() - 1);
        assertThat(item.getName()).isEqualTo(itemRequest.getName());
        assertThat(item.getDescription()).isEqualTo(itemRequest.getDescription());
    }

    @Test
    public void deleteTodoItemTest() {
        TodoItem item = createTestTodoItem();
        assertThat(getTodoItemRepository().findAll().size()).isEqualTo(1);
        todoItemService.delete(item.getId());
        assertThat(getTodoItemRepository().findAll().size()).isEqualTo(0);
    }

    @Test
    public void changeStatusTest() {
        createTestTodoItem();
        List<TodoItem> items = getTodoItemRepository().findAll();
        TodoItem created = items.get(items.size() - 1);
        assertThat(false).isEqualTo(created.isDone());
        todoItemService.changeStatus(created.getId());
        List<TodoItem> changed = getTodoItemRepository().findAll();
        TodoItem item = changed.get(items.size() - 1);
        assertThat(item.isDone()).isTrue();
    }

    @Test
    public void editTodoItemTest() {
        TodoItem item = createTestTodoItem();
        TodoItem addedItem = getTodoItemRepository().getById(item.getId());
        assertThat(addedItem.getName()).isEqualTo(item.getName());
        assertThat(addedItem.getDescription()).isEqualTo(item.getDescription());

        TodoItem newItem = new TodoItem();
        newItem.setName("newName");
        newItem.setDescription("newDesc");
        TodoItemRequest req = new TodoItemRequest();
        req.setName(newItem.getName());
        req.setDescription(newItem.getDescription());
        req.setDate(item.getDate());
        todoItemService.update(item.getId(), req);
        TodoItem editedItem = getTodoItemRepository().getById(item.getId());
        assertThat(editedItem.getName()).isEqualTo(newItem.getName());
        assertThat(editedItem.getDescription()).isEqualTo(newItem.getDescription());
    }

    @Test
    public void getAllTest() {
        createTestTodoItem();
        assertThat(todoItemService.get().size()).isEqualTo(1);
    }

    @Test
    public void getOneTest() {
        TodoItem item = createTestTodoItem();
        TodoItem added = todoItemService.get(item.getId());
        assertThat(added.getName()).isEqualTo(item.getName());
        assertThat(added.getDescription()).isEqualTo(item.getDescription());
    }
}
