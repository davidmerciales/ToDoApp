package com.example.mvvmtodo.presenter.ui.screen.todayTask.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mvvmtodo.data.model.Todo

class TodayTaskContract {

    sealed interface TodayTaskEvent{
        data object OnBackClick: TodayTaskEvent
    }

    interface TodayTaskState{
        var taskToday: List<Todo>
    }

    class MutableTodayTaskState: TodayTaskState{
        override var taskToday: List<Todo> by mutableStateOf(emptyList())
    }
}