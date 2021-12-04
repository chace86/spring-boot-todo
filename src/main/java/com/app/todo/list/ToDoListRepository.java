package com.app.todo.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

    public List<ToDoList> findAllToDoListsByUsername(String username);
}
