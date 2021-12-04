package com.app.todo.list.item;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoListItemService {

    private final ToDoListItemRepository repository;

    public ToDoListItemService(ToDoListItemRepository repository) {
        this.repository = repository;
    }

    public boolean saveToDoListItem(ToDoListItem item) {
        if (repository.existsById(item.getId())) {
            // do not insert if entity already exists
            return false;
        }
        else {
            repository.save(item);
            return true;
        }
    }

    public void updateToDoListItem(ToDoListItem list) {
        repository.save(list);
    }

    public Optional<ToDoListItem> findToDoListItem(long id) {
        return repository.findById(id);
    }

    public void deleteToDoListItem(long id) {
        repository.deleteById(id);
    }
}
