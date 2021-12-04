package com.app.todo.item;

import com.app.todo.list.ToDoList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ToDoListItemServiceTests {

    private static final long ID = 1;
    private static final ToDoList TODO_LIST = new ToDoList(ID, "rjohnson", "chores", null);
    private static final ToDoListItem TODO_LIST_ITEM = new ToDoListItem(ID, TODO_LIST, "test code", false);

    @Mock
    private ToDoListItemRepository repository;

    @InjectMocks
    private ToDoListItemService service;

    @Test
    void testSaveToDoList() {
        given(repository.existsById(ID)).willReturn(false);

        assertTrue(service.saveToDoListItem(TODO_LIST_ITEM));
        verify(repository, times(1)).existsById(ID);
        verify(repository, times(1)).save(TODO_LIST_ITEM);
    }

    @Test
    void testSaveToDoListAlreadyExistsFalse() {
        given(repository.existsById(ID)).willReturn(true);

        assertFalse(service.saveToDoListItem(TODO_LIST_ITEM));
        verify(repository, times(1)).existsById(ID);
        verify(repository, times(0)).save(TODO_LIST_ITEM);
    }

    @Test
    void testUpdateToDoList() {
        given(repository.findById(ID)).willReturn(Optional.of(TODO_LIST_ITEM));
        String description = "test description";
        ToDoListItem newTodoListItem =
                new ToDoListItem(TODO_LIST_ITEM.getId(), TODO_LIST, description, true);

        assertTrue(service.updateToDoListItem(ID, description, true));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(newTodoListItem);
    }

    @Test
    void testUpdateToDoListNotFound() {
        given(repository.findById(ID)).willReturn(Optional.empty());
        String description = "test description";
        ToDoListItem newTodoListItem =
                new ToDoListItem(TODO_LIST_ITEM.getId(), TODO_LIST, description, true);

        assertFalse(service.updateToDoListItem(ID, description, true));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(0)).save(newTodoListItem);
    }

    @Test
    void testFindToDoListItem() {
        given(repository.findById(ID)).willReturn(Optional.of(TODO_LIST_ITEM));

        assertEquals(Optional.of(TODO_LIST_ITEM), service.findToDoListItem(ID));
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void testDeleteToDoList() {
        given(repository.existsById(ID)).willReturn(true);

        service.deleteToDoListItem(ID);
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteToDoListNotFound() {
        given(repository.existsById(ID)).willReturn(false);

        service.deleteToDoListItem(ID);
        verify(repository, times(0)).deleteById(ID);
    }
}
