package com.example.mvvmtodo.presenter.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.Routes
import com.example.mvvmtodo.utils.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appController: AppController
): ViewModel(), MyController by AppController(){
    val state: LoginContract.LoginState = LoginContract.MutableLoginState()

    init {

    }

    fun onEvent(event: LoginContract.LoginEvent){
        when(event){
            LoginContract.LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    appController.sendUiEvent(NavEvent.Navigate(Routes.TODO_LIST))
                }
            }
        }
    }
}