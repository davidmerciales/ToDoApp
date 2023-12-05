package com.example.mvvmtodo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmtodo.data.dao.TodoDao
import com.example.mvvmtodo.data.model.Todo

@Database(
    entities = [Todo :: class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao : TodoDao
}