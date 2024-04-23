package com.example.mvvmtodo.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val description: String,
    val date: String,
    val isDone: Boolean,
    val priority: Int,
    val color: Int,
    @PrimaryKey val id: Int? = null
)

