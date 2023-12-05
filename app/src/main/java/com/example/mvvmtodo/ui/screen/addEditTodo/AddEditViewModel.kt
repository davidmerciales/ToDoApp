package com.example.mvvmtodo.ui.screen.addEditTodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.repository.TodoRepository
import com.example.mvvmtodo.utils.GetDateTimeHelper
import com.example.mvvmtodo.utils.UiEvent
import com.example.mvvmtodo.utils.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var todo by mutableStateOf<Todo?> (null)
    private set

    var title by mutableStateOf<String>("")
    private set

    var description by mutableStateOf<String>("")
    private set

    var priority by mutableIntStateOf(0)
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        val todoId = savedStateHandle.get<Int>("todoId")
        if(todoId != -1){
            viewModelScope.launch {
                repository.getTodoById(todoId).let {
                    todo ->
                    title = todo.title
                    description = todo.description
                    this@AddEditViewModel.todo = todo
                }
            }
        }
    }

    fun OnEvent(event: AddEditEvent){
        when(event){
            is AddEditEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditEvent.OnPriorityChange -> {
                priority = event.priority
            }
            is AddEditEvent.OnSaveTodo -> {
                val currentDateTime = LocalDateTime.now().toDateString()

                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar("Title must not be empty"))
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            date = currentDateTime,
                            priority = priority,
                            id = todo?.id
                        )
                    )
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
        }

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
