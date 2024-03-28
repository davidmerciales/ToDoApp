package com.example.mvvmtodo.utils

sealed class MessageEvent {
    data class ShowSnackBar(val message: String, val action: String? = null) : MessageEvent()
    data class ShowToastMessage(val message: String) : MessageEvent()
}