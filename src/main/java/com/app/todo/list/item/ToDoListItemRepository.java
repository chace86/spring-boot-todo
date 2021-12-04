package com.app.todo.list.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListItemRepository extends JpaRepository<ToDoListItem, Long> {
}
