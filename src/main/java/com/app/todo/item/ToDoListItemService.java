package com.app.todo.item;

import com.app.todo.list.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoListItemService {

    private final ToDoListItemRepository itemRepository;
    private final ToDoListRepository listRepository;

    public ToDoListItemService(ToDoListItemRepository itemRepository, ToDoListRepository listRepository) {
        this.itemRepository = itemRepository;
        this.listRepository = listRepository;
    }

    public boolean saveToDoListItem(ToDoListItem item) {
        // check that the item does not already exist and that associated list relationship exists
        boolean isInsertable =
                !itemRepository.existsById(item.getId()) && listRepository.existsById(item.getList().getId());

        if (isInsertable) {
            itemRepository.save(item);
        }

        return isInsertable;
    }

    public boolean updateToDoListItem(long id, String description, boolean isCompleted) {
        return itemRepository.findById(id)
                .map(item -> {
                    ToDoListItem updatedItem = new ToDoListItem(item.getId(), item.getList(), description, isCompleted);
                    itemRepository.save(updatedItem);
                    return true;
                })
                .orElse(false);
    }

    public Optional<ToDoListItem> findToDoListItem(long id) {
        return itemRepository.findById(id);
    }

    public boolean deleteToDoListItem(long id) {
        boolean isPresent = itemRepository.existsById(id);

        if (isPresent) {
            itemRepository.deleteById(id);
        }

        return isPresent;
    }
}
