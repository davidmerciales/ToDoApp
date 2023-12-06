package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mvvmtodo.data.model.Todo

class AddEditContract {

    sealed interface AddEditEvent {
        data class OnTitleChange(val title: String) : AddEditEvent

        data class OnDescriptionChange(val description: String) : AddEditEvent

        data class OnPriorityChange(val priority: Int) : AddEditEvent

        data object OnSaveTodo : AddEditEvent
    }

    interface AddEditState {
        var todo: Todo?
        var title: String
        var description: String
        var priority: Int
        var expanded: Boolean
        var selectedText: String
        var priorities: List<String>
        var snackbarHostState: SnackbarHostState
    }

    class MutableAddEditState : AddEditState {
        override var todo: Todo? by mutableStateOf(null)
        override var title: String by mutableStateOf("")
        override var description: String by mutableStateOf("")
        override var priority: Int by mutableIntStateOf(0)
        override var expanded: Boolean by mutableStateOf(false)
        override var selectedText: String by mutableStateOf("Select Priority")
        override var priorities: List<String> by mutableStateOf(
            listOf(
                "Critical",
                "High",
                "Medium",
                "Low"
            )
        )
        override var snackbarHostState: SnackbarHostState = SnackbarHostState()

    }
}