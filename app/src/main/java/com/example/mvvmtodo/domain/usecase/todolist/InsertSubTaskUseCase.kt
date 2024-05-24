package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.TodoWithSubtask
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class InsertSubTaskUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(subtask: List<Subtask>) {
        repository.insertSubTask(
            subtask
        )
    }
}