package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.domain.usecase.todolist.InsertToDoUseCase
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.MessageEvent
import com.example.mvvmtodo.utils.NavEvent
import com.example.mvvmtodo.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

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
                    state.isDone = todo.isDone
                    this@AddEditViewModel.state.todo = todo
                }
            }
        }
    }

    fun onEvent(event: AddEditContract.AddEditEvent) {
        when (event) {
            is AddEditContract.AddEditEvent.OnTitleChange -> {
                state.title = event.title
            }

            is AddEditContract.AddEditEvent.OnDescriptionChange -> {
                state.description = event.description
            }

            AddEditContract.AddEditEvent.OnCompletedChange -> {
                state.isDone = !state.isDone
            }

            is AddEditContract.AddEditEvent.OnPriorityChange -> {
                state.priority = event.priority
            }

            is AddEditContract.AddEditEvent.OnSaveTodo -> {
                val currentDateTime = LocalDateTime.now().toDateString()

                state.taskColor = Color(
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256)
                ).toArgb()

                viewModelScope.launch {
                    if (state.title.isBlank()) {
                        sendUiEvent(MessageEvent.ShowToastMessage("Title must not be empty!"))
                        return@launch
                    }
                    insertToDoUseCase(
                        Todo(
                            title = state.title,
                            description = state.description,
                            isDone = state.isDone,
                            date = currentDateTime,
                            priority = state.priority,
                            id = state.todo?.id,
                            color = state.taskColor
                        ), state.isDone
                    )
                    sendUiEvent(NavEvent.PopBackStack)
                }
            }
        }

    }
}
