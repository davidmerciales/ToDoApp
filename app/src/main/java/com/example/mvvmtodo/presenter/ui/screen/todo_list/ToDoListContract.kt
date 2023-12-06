package com.example.mvvmtodo.presenter.ui.screen.todo_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mvvmtodo.data.model.Todo

class ToDoListContract {

    sealed interface TodoListEvent {
        data class OnDeleteTodo(val todo: Todo) : TodoListEvent
        data class OnDoneTodo(val todo: Todo, val isChecked: Boolean) : TodoListEvent
        data class OnTodoItemClick(val todo: Todo) : TodoListEvent
        data class OnSortClick(val sort: Int?=0) : TodoListEvent
        data object OnAddEditTodo : TodoListEvent
        data object OnUndoDelete : TodoListEvent
        data object OnCompletedNavClick : TodoListEvent
        data object OnToDoNavClick : TodoListEvent
    }

    interface ToDoListState {
        var todos: List<Todo>
        var deletedToDo: Todo?
        var sortIsClicked: Boolean
        var snackbarHostState: SnackbarHostState
    }

    class MutableToDoListState : ToDoListState {
        override var todos: List<Todo> by mutableStateOf(emptyList())
        override var deletedToDo: Todo? by mutableStateOf(null)
        override var sortIsClicked: Boolean by mutableStateOf(false)
        override var snackbarHostState: SnackbarHostState = SnackbarHostState()
    }
}