package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.model.TodoWithSubtask

class AddEditContract {

    sealed interface AddEditEvent {
        data class OnTitleChange(val title: String) : AddEditEvent

        data class OnDescriptionChange(val description: String) : AddEditEvent
        data class OnSubTaskDescriptionChange(val subtaskDescription: String) : AddEditEvent

        data object OnCompletedChange : AddEditEvent
        data class OnSubTaskCompletedChange(val isDone: Boolean, val taskId: Long) : AddEditEvent

        data class OnPriorityChange(val priority: Int) : AddEditEvent

        data object OnSaveTodo : AddEditEvent
        data class OnSaveSubTask(val subtaskId: Int) : AddEditEvent
        data class OnProgressChange(val progress: Float) : AddEditEvent
        data object OnProgressFinished : AddEditEvent
        data class OnDeleteSubtask(val id: Int): AddEditEvent
    }

    interface AddEditState {
        var todo: Todo?
        var todos: List<Subtask>
        var title: String
        var description: String
        var priority: Int
        var taskColor: Int
        var expanded: Boolean
        var selectedText: String
        var priorities: List<String>
        var snackbarHostState: SnackbarHostState
        var isDone: Boolean
        var progress: Float
        var isSubtask: Boolean
        var subtaskDescription: String
        var isSubtaskDone: Boolean
        var subtasks: List<Subtask>
    }

    class MutableAddEditState : AddEditState {
        override var todo: Todo? by mutableStateOf(null)
        override var todos: List<Subtask> by mutableStateOf(emptyList())
        override var title: String by mutableStateOf("")
        override var description: String by mutableStateOf("")
        override var priority: Int by mutableIntStateOf(0)
        override var taskColor: Int by mutableIntStateOf(0)
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
        override var isDone: Boolean by mutableStateOf(false)
        override var progress: Float by mutableFloatStateOf(0f)
        override var isSubtask: Boolean by mutableStateOf(false)
        override var subtaskDescription: String by mutableStateOf("")
        override var isSubtaskDone: Boolean by mutableStateOf(false)
        override var subtasks: List<Subtask> by mutableStateOf(emptyList())
    }
}