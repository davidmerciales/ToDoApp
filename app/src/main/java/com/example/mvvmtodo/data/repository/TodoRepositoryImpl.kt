package com.example.mvvmtodo.data.repository

import com.example.mvvmtodo.data.dao.TodoDao
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {

    override suspend fun getTodoById(id: Int?): Todo {
        return dao.getTodoById(id)

    }

    override suspend fun getSortedTodosByDesc(): List<Todo> {
        return dao.getSortedTodosByDesc()
    }

    override suspend fun getSortedTodosByAsc(): List<Todo> {
        return dao.getSortedTodosByAsc()
    }

    override suspend fun getSortedTodosByRecent(): List<Todo> {
        return dao.getSortedTodosByRecent()
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }
}