package de.biga.ssodemo.app;

import de.biga.ssodemo.web.TodoController;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {
    private static final List<Todo> TODOS = new ArrayList<>();

    public List<Todo> getAllTodos() {
        return new ArrayList<>(TODOS);
    }

    public Todo findTodoById(int id) {
        return TODOS.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(""));
    }

    public void addTodo(Todo todo) {
        todo.setId(TODOS.size() + 1);
        TODOS.add(todo);
    }
}
