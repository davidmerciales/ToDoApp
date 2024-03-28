package com.example.mvvmtodo.utils

sealed class NavEvent {
    data object PopBackStack : NavEvent()
    data class Navigate(val route: String) : NavEvent()
}