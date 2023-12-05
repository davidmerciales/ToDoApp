package com.example.mvvmtodo.data.repository

import androidx.room.Query
import com.example.mvvmtodo.data.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int?) : Todo

    suspend fun getSortedTodosByDesc() : List<Todo>

    suspend fun getSortedTodosByAsc() : List<Todo>

    suspend fun getSortedTodosByRecent() : List<Todo>

    fun getTodos(): Flow<List<Todo>>
}