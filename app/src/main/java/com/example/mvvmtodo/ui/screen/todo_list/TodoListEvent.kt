package com.example.mvvmtodo.ui.screen.todo_list

import com.example.mvvmtodo.data.model.Todo

sealed class TodoListEvent {
    data class OnDeleteTodo(val todo: Todo) : TodoListEvent()

    data class OnDoneTodo(val todo: Todo, val isChecked : Boolean) : TodoListEvent()

    data class OnTodoItemClick(val todo: Todo) : TodoListEvent()

    data class OnSortClick(val sort: Int) : TodoListEvent()

    data object OnAddEditTodo : TodoListEvent()

    data object OnUndoDelete : TodoListEvent()
}