package com.example.mvvmtodo.presenter.ui.screen.completedTodo

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mvvmtodo.data.model.Todo

class CompletedToDoContract {
    sealed interface CompletedToEvent {
        data class OnDeleteTodo(val todo: Todo) : CompletedToEvent
        data class OnDoneTodo(val todo: Todo, val isChecked: Boolean) : CompletedToEvent
        data object OnAddEditTodo : CompletedToEvent
        data object OnUndoDelete : CompletedToEvent
        data object OnToDoNavClick : CompletedToEvent
    }

    interface CompletedToDoState {
        var todos: List<Todo>
        var deletedToDo: Todo?
        var sortIsClicked: Boolean
        var snackbarHostState: SnackbarHostState
    }

    class MutableCompletedToDoListState : CompletedToDoState {
        override var todos: List<Todo> by mutableStateOf(emptyList())
        override var deletedToDo: Todo? by mutableStateOf(null)
        override var sortIsClicked: Boolean by mutableStateOf(false)
        override var snackbarHostState: SnackbarHostState = SnackbarHostState()
    }
}