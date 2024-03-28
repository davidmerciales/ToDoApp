package com.example.mvvmtodo.presenter.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginContract {
    interface LoginEvent {
        data object OnLoginClick : LoginEvent
    }

    interface LoginState {
        var email: String
        var password: String
        var isPasswordVisible: Boolean
    }

    class MutableLoginState: LoginState{
        override var email: String by mutableStateOf("")
        override var password: String by mutableStateOf("")
        override var isPasswordVisible: Boolean by mutableStateOf(false)
    }
}