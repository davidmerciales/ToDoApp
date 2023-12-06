package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.domain.usecase.todolist.InsertToDoUseCase
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.UiEvent
import com.example.mvvmtodo.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val insertToDoUseCase: InsertToDoUseCase,
    savedStateHandle: SavedStateHandle,
    appController: AppController
) : ViewModel(), MyController by appController {
    val state: AddEditContract.AddEditState = AddEditContract.MutableAddEditState()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId).let { todo ->
                    state.title = todo.title
                    state.description = todo.description
                    this@AddEditViewModel.state.todo = todo
                }
            }
        }
    }

    fun OnEvent(event: AddEditContract.AddEditEvent) {
        when (event) {
            is AddEditContract.AddEditEvent.OnTitleChange -> {
                state.title = event.title
            }

            is AddEditContract.AddEditEvent.OnDescriptionChange -> {
                state.description = event.description
            }

            is AddEditContract.AddEditEvent.OnPriorityChange -> {
                state.priority = event.priority
            }

            is AddEditContract.AddEditEvent.OnSaveTodo -> {
                val currentDateTime = LocalDateTime.now().toDateString()

                viewModelScope.launch {
                    if (state.title.isBlank()) {
                        sendUiEvent(UiEvent.ShowToastMessage("Title must not be empty!"))
                        return@launch
                    }
                    insertToDoUseCase(
                        Todo(
                            title = state.title,
                            description = state.description,
                            isDone = state.todo?.isDone ?: false,
                            date = currentDateTime,
                            priority = state.priority,
                            id = state.todo?.id
                        ), state.todo?.isDone ?: false
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }

    }
}
