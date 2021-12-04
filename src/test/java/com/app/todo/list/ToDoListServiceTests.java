package com.app.todo.list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceTests {

    private static final long ID = 1;
    private static final ToDoList TODO_LIST = new ToDoList(ID, "rjohnson", "chores", null);
    private static final List<ToDoList> TO_DO_LIST_LIST = Collections.singletonList(TODO_LIST);

    @Mock
    private ToDoListRepository repository;

    @InjectMocks
    private ToDoListService service;

    @Test
    void testSaveToDoList() {
        given(repository.existsById(ID)).willReturn(false);

        assertTrue(service.saveToDoList(TODO_LIST));
        verify(repository, times(1)).existsById(ID);
        verify(repository, times(1)).save(TODO_LIST);
    }

    @Test
    void testSaveToDoListAlreadyExistsFalse() {
        given(repository.existsById(ID)).willReturn(true);

        assertFalse(service.saveToDoList(TODO_LIST));
        verify(repository, times(1)).existsById(ID);
        verify(repository, times(0)).save(TODO_LIST);
    }

    @Test
    void testUpdateToDoList() {
        given(repository.findById(ID)).willReturn(Optional.of(TODO_LIST));
        String title = "test";
        ToDoList newTodoList =
                new ToDoList(TODO_LIST.getId(), TODO_LIST.getUsername(), title, TODO_LIST.getItems());

        assertTrue(service.updateToDoList(ID, title));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(newTodoList);
    }

    @Test
    void testUpdateToDoListNotFound() {
        given(repository.findById(ID)).willReturn(Optional.empty());
        String title = "test";
        ToDoList newTodoList =
                new ToDoList(TODO_LIST.getId(), TODO_LIST.getUsername(), title, TODO_LIST.getItems());

        assertFalse(service.updateToDoList(ID, title));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(0)).save(newTodoList);
    }

    @Test
    void testFindAllToDoListsByUsername() {
        String username = "username";
        given(repository.findAllToDoListsByUsername(username)).willReturn(TO_DO_LIST_LIST);

        assertEquals(TO_DO_LIST_LIST, service.findAllToDoListsByUsername(username));
        verify(repository, times(1)).findAllToDoListsByUsername(username);
    }

    @Test
    void testDeleteToDoList() {
        given(repository.existsById(ID)).willReturn(true);

        service.deleteToDoList(ID);
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteToDoListNotFound() {
        given(repository.existsById(ID)).willReturn(false);

        service.deleteToDoList(ID);
        verify(repository, times(0)).deleteById(ID);
    }
}
