package com.example.mvvmtodo.presenter.ui.navigation

import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mvvmtodo.utils.MessageEvent
import com.example.mvvmtodo.utils.NavEvent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MyController {
    suspend fun sendUiEvent(event: NavEvent)
    suspend fun sendUiEvent(event: MessageEvent)
}

@ActivityRetainedScoped
class AppController @Inject constructor(
) : MyController {

    private val _uiEvent = Channel<NavEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _msgEvent = Channel<MessageEvent>()
    val msgEvent = _msgEvent.receiveAsFlow()

    lateinit var navController: NavController

    override suspend fun sendUiEvent(event: NavEvent) {
        _uiEvent.send(event)
    }

    override suspend fun sendUiEvent(event: MessageEvent) {
        _msgEvent.send(event)
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AppController.CollectRoutes(
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is NavEvent.PopBackStack -> navController.popBackStack()
                is NavEvent.Navigate -> navController.navigate(event.route)
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AppController.CollectMessages(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    onResult: (SnackbarResult) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        msgEvent.collect { event ->
            when (event) {
                is MessageEvent.ShowSnackBar -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                            duration = SnackbarDuration.Short
                        )
                        onResult(result)
                    }
                }

                is MessageEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}