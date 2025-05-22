package no.computas.backend.service

import no.computas.backend.model.Todo
import no.computas.backend.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun getAllTodos(): List<Todo> = todoRepository.findAll()

    fun getTodoById(id: Long): Todo? = todoRepository.findById(id).orElse(null)

    fun createTodo(todo: Todo): Todo = todoRepository.save(todo)

    fun updateTodo(id: Long, updatedTodo: Todo): Todo? {
        val existingTodo = todoRepository.findById(id).orElse(null) ?: return null
        existingTodo.title = updatedTodo.title
        return todoRepository.save(existingTodo)
    }

    fun deleteTodoById(id: Long) {
        todoRepository.deleteById(id)
    }
}
