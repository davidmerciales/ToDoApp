package com.example.mvvmtodo.presenter.ui.screen.todayTask.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.domain.repository.TodoRepository
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.GetDateTimeHelper
import com.example.mvvmtodo.utils.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TodayTaskViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val appController: AppController
): ViewModel(), MyController by appController {
    val state : TodayTaskContract.TodayTaskState = TodayTaskContract.MutableTodayTaskState()

    init {
        initTaskTodayList()
    }

    fun onEvent(event: TodayTaskContract.TodayTaskEvent){
        when(event){
            TodayTaskContract.TodayTaskEvent.OnBackClick->{
                viewModelScope.launch {
                    appController.sendUiEvent(NavEvent.PopBackStack)
                }
            }
        }
    }

    private fun initTaskTodayList() = viewModelScope.launch {
        todoRepository.getTodos().collect{taskTodayRaw->
            state.taskToday = taskTodayRaw.filter {
                it.dateCreated.contains(GetDateTimeHelper().getCurrentDateTime("yyyy-MM-dd"))
            }
        }
    }
}