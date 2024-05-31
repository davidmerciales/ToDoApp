package com.example.mvvmtodo.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Todo(
    val title: String,
    val description: String,
    val dateCreated: String,
    val dueDate: String,
    val isDone: Boolean,
    val priority: Int,
    val color: Int,
    val progress: Float,
    val isSubtask: Boolean,
    @PrimaryKey val id: Int? = null
)

data class TodoWithSubtask(
    @Embedded val todo: Todo,
    @Relation(
        parentColumn = "id",
        entityColumn = "todoID"
    )
    val subTask: List<Subtask>
)

