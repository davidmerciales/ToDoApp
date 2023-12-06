package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.domain.usecase.todolist.DeleteToDoUseCase
import com.example.mvvmtodo.domain.usecase.todolist.InsertToDoUseCase
import com.example.mvvmtodo.domain.usecase.todolist.SortToDoListUseCase
import com.example.mvvmtodo.domain.usecase.todolist.UndoDeleteUseCase
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.Routes
import com.example.mvvmtodo.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val sortToDoListUseCase: SortToDoListUseCase,
    private val deleteToDoUseCase: DeleteToDoUseCase,
    private val insertToDoUseCase: InsertToDoUseCase,
    private val undoDeleteUseCase: UndoDeleteUseCase,
    appController: AppController
) : ViewModel(), MyController by appController {
    val state: ToDoListContract.ToDoListState = ToDoListContract.MutableToDoListState()

    init {
        viewModelScope.launch {
            repository.getTodos().collect { todoListRaw ->
                state.todos = todoListRaw
            }
        }
    }

    fun onEvent(event: ToDoListContract.TodoListEvent) {
        when (event) {
            is ToDoListContract.TodoListEvent.OnSortClick -> {
                Log.d("ViewModel", state.sortIsClicked.toString() + "Sort Val: "+event.sort)
                viewModelScope.launch {
                    state.todos = if (event.sort != null && event.sort != 0) {
                        sortToDoListUseCase(event.sort)
                    } else {
                        val sortBy = if (state.sortIsClicked) 1 else 0
                        sortToDoListUseCase(sortBy)
                    }
                    state.sortIsClicked = !state.sortIsClicked
                }
            }

            is ToDoListContract.TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    state.deletedToDo = event.todo
                    deleteToDoUseCase(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("Item Deleted!", "Undo"))
                }
            }

            is ToDoListContract.TodoListEvent.OnDoneTodo -> {
                viewModelScope.launch {
                    insertToDoUseCase(event.todo, event.isChecked)
                }
            }

            is ToDoListContract.TodoListEvent.OnTodoItemClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
                }

            }

            is ToDoListContract.TodoListEvent.OnAddEditTodo -> {
                Log.d("OnAddEvent", "TodoViewModel: ")
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
                }
            }

            is ToDoListContract.TodoListEvent.OnUndoDelete -> {
                state.deletedToDo?.let {
                    viewModelScope.launch {
                        undoDeleteUseCase(it)
                        sendUiEvent(UiEvent.ShowSnackBar("Undo Deleted Item"))
                    }
                }
            }

            is ToDoListContract.TodoListEvent.OnCompletedNavClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.COMPLETED_TODO))
                }
            }

            is ToDoListContract.TodoListEvent.OnToDoNavClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                }
            }
        }
    }
}