package com.app.todo.item;

import com.app.todo.list.ToDoList;
import com.app.todo.list.ToDoListRepository;
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
    private ToDoListItemRepository itemRepository;
    @Mock
    private ToDoListRepository listRepository;

    @InjectMocks
    private ToDoListItemService service;

    @Test
    void testSaveToDoListItem() {
        given(itemRepository.existsById(ID)).willReturn(false);
        given(listRepository.existsById(ID)).willReturn(true);

        assertTrue(service.saveToDoListItem(TODO_LIST_ITEM));
        verify(itemRepository, times(1)).existsById(ID);
        verify(listRepository, times(1)).existsById(ID);
        verify(itemRepository, times(1)).save(TODO_LIST_ITEM);
    }

    @Test
    void testSaveToDoListItemAlreadyExistsFalse() {
        given(itemRepository.existsById(ID)).willReturn(true);

        assertFalse(service.saveToDoListItem(TODO_LIST_ITEM));
        verify(itemRepository, times(1)).existsById(ID);
        verify(listRepository, times(0)).existsById(ID);
        verify(itemRepository, times(0)).save(TODO_LIST_ITEM);
    }

    @Test
    void testSaveToDoListItemParentListDoesNotExist() {
        given(itemRepository.existsById(ID)).willReturn(false);
        given(listRepository.existsById(ID)).willReturn(false);

        assertFalse(service.saveToDoListItem(TODO_LIST_ITEM));
        verify(itemRepository, times(1)).existsById(ID);
        verify(listRepository, times(1)).existsById(ID);
        verify(itemRepository, times(0)).save(TODO_LIST_ITEM);
    }

    @Test
    void testUpdateToDoListItem() {
        given(itemRepository.findById(ID)).willReturn(Optional.of(TODO_LIST_ITEM));
        String description = "test description";
        ToDoListItem newTodoListItem =
                new ToDoListItem(TODO_LIST_ITEM.getId(), TODO_LIST, description, true);

        assertTrue(service.updateToDoListItem(ID, description, true));
        verify(itemRepository, times(1)).findById(ID);
        verify(itemRepository, times(1)).save(newTodoListItem);
    }

    @Test
    void testUpdateToDoListItemNotFound() {
        given(itemRepository.findById(ID)).willReturn(Optional.empty());
        String description = "test description";
        ToDoListItem newTodoListItem =
                new ToDoListItem(TODO_LIST_ITEM.getId(), TODO_LIST, description, true);

        assertFalse(service.updateToDoListItem(ID, description, true));
        verify(itemRepository, times(1)).findById(ID);
        verify(itemRepository, times(0)).save(newTodoListItem);
    }

    @Test
    void testFindToDoListItem() {
        given(itemRepository.findById(ID)).willReturn(Optional.of(TODO_LIST_ITEM));

        assertEquals(Optional.of(TODO_LIST_ITEM), service.findToDoListItem(ID));
        verify(itemRepository, times(1)).findById(ID);
    }

    @Test
    void testDeleteToDoListItem() {
        given(itemRepository.existsById(ID)).willReturn(true);

        service.deleteToDoListItem(ID);
        verify(itemRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteToDoListItemNotFound() {
        given(itemRepository.existsById(ID)).willReturn(false);

        service.deleteToDoListItem(ID);
        verify(itemRepository, times(0)).deleteById(ID);
    }
}
