package com.example.mvvmtodo.domain.usecase.todolist

import com.example.mvvmtodo.data.repository.TodoRepositoryImpl
import com.example.mvvmtodo.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteSubtaskByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(subTaskId: Int) {
        repository.deleteSubtaskById(subTaskId)
    }
}