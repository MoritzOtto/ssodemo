package de.biga.ssodemo.web;

import de.biga.ssodemo.app.Todo;
import de.biga.ssodemo.app.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> allTodos = todoService.getAllTodos();
        return ResponseEntity.ok(allTodos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getSingleTodo(@PathVariable("id") int id) {
        var todo = todoService.findTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<Void> addTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Todo> update(@RequestBody Todo todo) {
        var todoToUpdate = todoService.findTodoById(todo.getId());
        todoToUpdate.setTitle(todo.getTitle());
        todoToUpdate.setDescription(todo.getDescription());
        todoToUpdate.setDone(todo.isDone());

        return ResponseEntity.ok(todoToUpdate);
    }
}
