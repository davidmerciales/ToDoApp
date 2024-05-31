package com.example.mvvmtodo.domain.repository

import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.model.TodoWithSubtask
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)
    suspend fun insertSubTask(subtask: List<Subtask>)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int?): Todo

    suspend fun getTodoWithSubtask(taskId: Int): Flow<List<TodoWithSubtask>>
    suspend fun getSortedTodosByDesc(): List<Todo>

    suspend fun getSortedTodosByAsc(): List<Todo>

    suspend fun getSortedTodosByRecent(): List<Todo>
    suspend fun deleteSubtaskById(id: Int?)

    fun getTodos(): Flow<List<Todo>>
}