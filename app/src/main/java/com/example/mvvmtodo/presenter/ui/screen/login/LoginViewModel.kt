package com.example.mvvmtodo.presenter.ui.screen.login

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.MyController
import com.example.mvvmtodo.utils.MessageEvent
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
            LoginContract.LoginEvent.OnLoginClick -> initializeLogin()
        }
    }

    private fun initializeLogin() = viewModelScope.launch {
        if (state.email == "admin" && state.password == "admin"){
            appController.sendUiEvent(NavEvent.Navigate(Routes.TODO_LIST))
        }else {
            appController.sendUiEvent(MessageEvent.ShowToastMessage("Incorrect username or password!"))
        }
    }
}