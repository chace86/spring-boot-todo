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
        if (repository.existsById(list.getId())) {
            // do not insert if entity already exists
            return false;
        }
        else {
            repository.save(list);
            return true;
        }
    }

    public void updateToDoList(ToDoList list) {
        repository.save(list);
    }

    public List<ToDoList> findAllToDoListsByUsername(String username) {
        return repository.findAllToDoListsByUsername(username);
    }

    public void deleteToDoList(long id) {
        repository.deleteById(id);
    }
}
