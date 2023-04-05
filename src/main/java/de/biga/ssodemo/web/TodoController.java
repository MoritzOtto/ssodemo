package de.biga.ssodemo.web;

import de.biga.ssodemo.app.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController("api/todo")
public class TodoController {
    private static final List<Todo> TODOS = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(TODOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getSingleTodo(@PathVariable("id") int id) {
        var todo = findTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<Void> addTodo(Todo todo) {
        todo.setId(TODOS.size() + 1);
        TODOS.add(todo);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Todo> update(Todo todo) {
        var todoToUpdate = findTodoById(todo.getId());
        todoToUpdate.setHeader(todo.getHeader());
        todoToUpdate.setBody(todo.getBody());
        todoToUpdate.setDone(todo.isDone());

        return ResponseEntity.ok(todoToUpdate);
    }

    private static Todo findTodoById(int id) {
        return TodoController.TODOS.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(""));
    }
}
