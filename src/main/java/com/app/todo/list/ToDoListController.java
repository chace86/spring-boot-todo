package com.app.todo.list;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/list")
public class ToDoListController {

    private final ToDoListService service;

    public ToDoListController(ToDoListService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> saveToDoList(@Valid @RequestBody ToDoList list) {
        boolean isCreated = service.saveToDoList(list);

        if (isCreated) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(list.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateToDoList(@PathVariable long id, @RequestParam String title) {
        boolean isUpdated = service.updateToDoList(id, title);
        return isUpdated ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}")
    public List<ToDoList> findAllToListsByUsername(@PathVariable String username) {
        return service.findAllToDoListsByUsername(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToDoList(@PathVariable long id) {
        boolean isDeleted = service.deleteToDoList(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
