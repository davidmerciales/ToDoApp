package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.model.TodoWithSubtask
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.domain.usecase.todolist.InsertSubTaskUseCase
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
    private val insertSubTaskUseCase: InsertSubTaskUseCase,
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
                    state.progress = todo.progress
                    state.taskColor = todo.color
                    this@AddEditViewModel.state.todo = todo
                }
            }
        }

        initGetTodos(todoId)
    }

    private fun initGetTodos(todoId: Int?) = viewModelScope.launch {
        todoId?.let {
            repository.getTodoWithSubtask(it).collect { todoListRaw ->
                todoListRaw.map { task->
                    state.subtasks = task.subTask
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

            is AddEditContract.AddEditEvent.OnSubTaskDescriptionChange -> {
                state.subtaskDescription = event.subtaskDescription
            }

            AddEditContract.AddEditEvent.OnCompletedChange -> {
                state.isDone = !state.isDone
                if (state.isDone){
                    state.progress = 1f
                }else{
                    state.progress = 0f
                }
            }

            is AddEditContract.AddEditEvent.OnSubTaskCompletedChange -> {
                Log.d("onEvents: ", "zxc: ")
                state.subtasks = state.subtasks.map { subtask ->
                    if (subtask.subTaskID == event.taskId) {
                        Log.d("onEvents: ", "true: ")
                        subtask.copy(isDone = !event.isDone)
                    } else {
                        Log.d("onEvents: ", "false: ")
                        subtask
                    }
                }
            }

            is AddEditContract.AddEditEvent.OnPriorityChange -> {
                state.priority = event.priority
            }

            AddEditContract.AddEditEvent.OnSaveSubTask -> {
                val currentDateTime = LocalDateTime.now().toDateString()
                state.subtasks = state.subtasks.plus(
                    Subtask(
                        todoID = state.todo?.id?:-1,
                        description = state.subtaskDescription,
                        dateCreated = currentDateTime,
                        isDone = state.isSubtaskDone
                    )
                )
//                viewModelScope.launch {
//                    insertSubTaskUseCase.invoke(
//                        Subtask(
//                            todoID = state.todo?.id?:-1,
//                            description = state.subtaskDescription,
//                            dateCreated = currentDateTime,
//                            isDone = false
//                        )
//                    )
//                }
            }

            is AddEditContract.AddEditEvent.OnSaveTodo -> {
                val currentDateTime = LocalDateTime.now().toDateString()

                if (state.taskColor == 0) {
                    state.taskColor = Color(
                        Random.nextInt(256),
                        Random.nextInt(256),
                        Random.nextInt(256)
                    ).toArgb()
                }

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
                            dateCreated = currentDateTime,
                            priority = state.priority,
                            id = state.todo?.id,
                            color = state.taskColor,
                            progress = state.progress,
                            dueDate = currentDateTime,
                            isSubtask = false
                        ), state.isDone
                    )
                    sendUiEvent(NavEvent.PopBackStack)

                }

                viewModelScope.launch {
                    insertSubTaskUseCase.invoke(
                        state.subtasks
                    )
                }
            }

            is AddEditContract.AddEditEvent.OnProgressChange -> {
                state.progress = event.progress
                if (state.isDone && state.progress!=1f){
                    state.isDone = false
                }
            }

            AddEditContract.AddEditEvent.OnProgressFinished -> {
                if (state.progress == 1f)
                    state.isDone = true
            }
        }

    }
}
