package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class SortToDoListUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(sortType: Int): List<Todo> {
        return when (sortType) {
            0 -> {
                repository.getSortedTodosByDesc()
            }

            1 -> {
                repository.getSortedTodosByAsc()
            }

            2 -> {
                repository.getSortedTodosByRecent()
            }

            else -> repository.getSortedTodosByDesc()
        }
    }

}