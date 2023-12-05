package com.example.mvvmtodo.ui.screen.addEditTodo

sealed class AddEditEvent {
    data class OnTitleChange(val title : String) : AddEditEvent()

    data class OnDescriptionChange(val description : String) : AddEditEvent()

    data class OnPriorityChange(val priority : Int) : AddEditEvent()

    data object OnSaveTodo : AddEditEvent()
}