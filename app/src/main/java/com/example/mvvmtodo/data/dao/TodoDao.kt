package com.example.mvvmtodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.model.TodoWithSubtask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("Select * from todo where id = :id")
    suspend fun getTodoById(id: Int?): Todo

    @Query("Select * from todo order by priority DESC")
    suspend fun getSortedTodosByDesc(): List<Todo>

    @Query("Select * from todo order by priority ASC")
    suspend fun getSortedTodosByAsc(): List<Todo>

    @Query("Select * from todo order by dateCreated DESC")
    suspend fun getSortedTodosByRecent(): List<Todo>

    @Transaction
    @Query("SELECT * FROM todo where id = :taskId")
    fun getTodoWithSubtask(taskId: Int): Flow<List<TodoWithSubtask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubtask(subtask: List<Subtask>)

    @Query("Delete from subtask where subTaskID = :id")
    suspend fun deleteSubtaskById(id: Int?)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("Select * from todo")
    fun getTodos(): Flow<List<Todo>>
}