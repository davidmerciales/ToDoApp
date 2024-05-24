package com.example.mvvmtodo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmtodo.data.dao.TodoDao
import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.Todo

@Database(
    entities = [Todo::class,
               Subtask::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: TodoDao
}