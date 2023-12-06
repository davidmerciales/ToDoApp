package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class UndoDeleteUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.insertTodo(todo)
    }
}