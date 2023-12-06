package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteToDoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }

}