package com.app.todo.list;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    private final ToDoListRepository repository;

    public ToDoListService(ToDoListRepository repository) {
        this.repository = repository;
    }

    public boolean saveToDoList(ToDoList list) {
        boolean isInsertable = !repository.existsById(list.getId());

        if (isInsertable) {
            repository.save(list);
        }

        return isInsertable;
    }

    public boolean updateToDoList(long id, String title) {
        return repository.findById(id)
                .map(list -> {
                    ToDoList updatedList = new ToDoList(list.getId(), list.getUsername(), title, list.getItems());
                    repository.save(updatedList);
                    return true;
                })
                .orElse(false);
    }

    public List<ToDoList> findAllToDoListsByUsername(String username) {
        return repository.findAllToDoListsByUsername(username);
    }

    public boolean deleteToDoList(long id) {
        boolean isPresent = repository.existsById(id);

        if (isPresent) {
            repository.deleteById(id);
        }

        return isPresent;
    }
}
