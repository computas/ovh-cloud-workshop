package no.computas.backend.controller

import no.computas.backend.model.Todo
import no.computas.backend.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getAllTodos(): List<Todo> = todoService.getAllTodos()

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> {
        val todo = todoService.getTodoById(id)
        return if (todo != null) ResponseEntity.ok(todo) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createTodo(@RequestBody todo: Todo): ResponseEntity<Todo> {
        val createdTodo = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody updatedTodo: Todo): ResponseEntity<Todo> {
        val todo = todoService.updateTodo(id, updatedTodo)
        return if (todo != null) ResponseEntity.ok(todo) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTodoById(@PathVariable id: Long): ResponseEntity<Void> {
        todoService.deleteTodoById(id)
        return ResponseEntity.noContent().build()
    }
}
