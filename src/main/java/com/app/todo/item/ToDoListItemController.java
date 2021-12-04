package com.app.todo.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/item")
public class ToDoListItemController {

    private final ToDoListItemService service;

    public ToDoListItemController(ToDoListItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> saveToDoItem (@Valid @RequestBody ToDoListItem item) {
        boolean isCreated = service.saveToDoListItem(item);

        if (isCreated) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(item.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateToDoItem (@PathVariable long id, @RequestParam String description, @RequestParam boolean isCompleted) {
        boolean isUpdated = service.updateToDoListItem(id, description, isCompleted);
        return isUpdated ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ToDoListItem findToDoListItem(@PathVariable long id) {
        return service.findToDoListItem(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found for ID " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToDoListItem(@PathVariable long id) {
        boolean isDeleted = service.deleteToDoListItem(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
