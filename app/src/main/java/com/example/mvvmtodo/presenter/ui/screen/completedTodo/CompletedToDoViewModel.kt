package com.example.mvvmtodo.presenter.ui.screen.completedTodo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.domain.usecase.todolist.DeleteToDoUseCase
import com.example.mvvmtodo.domain.usecase.todolist.InsertToDoUseCase
import com.example.mvvmtodo.domain.usecase.todolist.UndoDeleteUseCase
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.Routes
import com.example.mvvmtodo.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedToDoViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val deleteToDoUseCase: DeleteToDoUseCase,
    private val insertToDoUseCase: InsertToDoUseCase,
    private val undoDeleteUseCase: UndoDeleteUseCase,
    appController: AppController
) : ViewModel(), MyController by appController {
    val state: CompletedToDoContract.CompletedToDoState =
        CompletedToDoContract.MutableCompletedToDoListState()

    init {
        viewModelScope.launch {
            repository.getTodos().collect { todoListRaw ->
                state.todos = todoListRaw
            }
        }
    }

    fun onEvent(event: CompletedToDoContract.CompletedToEvent) {
        when (event) {
            is CompletedToDoContract.CompletedToEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    state.deletedToDo = event.todo
                    deleteToDoUseCase(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("Item Deleted!", "Undo"))
                }
            }

            is CompletedToDoContract.CompletedToEvent.OnDoneTodo -> {
                viewModelScope.launch {
                    insertToDoUseCase(event.todo, event.isChecked)
                }
            }

            is CompletedToDoContract.CompletedToEvent.OnAddEditTodo -> {
                Log.d("OnAddEvent", "TodoViewModel: ")
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
                }
            }

            is CompletedToDoContract.CompletedToEvent.OnUndoDelete -> {
                state.deletedToDo?.let {
                    viewModelScope.launch {
                        undoDeleteUseCase(it)
                        sendUiEvent(UiEvent.ShowSnackBar("Undo Deleted Item"))
                    }
                }
            }

            is CompletedToDoContract.CompletedToEvent.OnToDoNavClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                }
            }
        }
    }
}
