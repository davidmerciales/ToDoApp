package com.example.mvvmtodo.ui.screen.todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.data.repository.TodoRepository
import com.example.mvvmtodo.utils.Routes
import com.example.mvvmtodo.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var todos by mutableStateOf<List<Todo>>(emptyList())

    private var deletedTodo: Todo? = null

    init {
        viewModelScope.launch {
            repository.getTodos().collect { todoListRaw ->
                todos = todoListRaw
            }
        }

    }

    fun OnEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnSortClick -> {
                viewModelScope.launch {
                    when (event.sort) {
                        2 -> {
                            todos = repository.getSortedTodosByDesc()
                        }

                        1 -> {
                            todos = repository.getSortedTodosByAsc()
                        }

                        0 -> {
                            todos = repository.getSortedTodosByRecent()
                        }
                    }
                }
            }

            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Item Deleted!",
                            "Undo"
                        )
                    )
                }
            }

            is TodoListEvent.OnDoneTodo -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isChecked
                        )
                    )
                }
            }

            is TodoListEvent.OnTodoItemClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }

            is TodoListEvent.OnAddEditTodo -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListEvent.OnUndoDelete -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                        sendUiEvent(UiEvent.ShowSnackBar("Undo Deleted Item"))
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}