package com.app.todo.item;

import com.app.todo.list.ToDoList;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoListItemService {

    private final ToDoListItemRepository repository;

    public ToDoListItemService(ToDoListItemRepository repository) {
        this.repository = repository;
    }

    public boolean saveToDoListItem(ToDoListItem item) {

        boolean isInsertable = !repository.existsById(item.getId());

        if (isInsertable) {
            repository.save(item);
        }

        return isInsertable;
    }

    public boolean updateToDoListItem(long id, String description, boolean isCompleted) {
        return repository.findById(id)
                .map(item -> {
                    ToDoListItem updatedItem = new ToDoListItem(item.getId(), item.getList(), description, isCompleted);
                    repository.save(updatedItem);
                    return true;
                })
                .orElse(false);
    }

    public Optional<ToDoListItem> findToDoListItem(long id) {
        return repository.findById(id);
    }

    public boolean deleteToDoListItem(long id) {
        boolean isPresent = repository.existsById(id);

        if (isPresent) {
            repository.deleteById(id);
        }

        return isPresent;
    }
}
