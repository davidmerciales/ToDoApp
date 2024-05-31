package com.example.mvvmtodo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subtask(
    @PrimaryKey val subTaskID: Long? = null,
    val todoID: Int,
    val description: String,
    val dateCreated: String,
    val isDone: Boolean
)