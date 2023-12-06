package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class InsertToDoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo, isDone: Boolean) {
        repository.insertTodo(
            todo.copy(
                isDone = isDone
            )
        )
    }
}