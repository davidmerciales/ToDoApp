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
import com.example.mvvmtodo.utils.UiEvent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MyController {
    suspend fun sendUiEvent(event: UiEvent)
}
@ActivityRetainedScoped
class AppController @Inject constructor(
) : MyController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    lateinit var navController: NavController

    override suspend fun sendUiEvent(event: UiEvent){
        _uiEvent.send(event)
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AppController.CollectEvents(snackbarHostState: SnackbarHostState, onResult: (SnackbarResult) -> Unit) {
    val context = LocalContext.current
    val scope  = rememberCoroutineScope()
    LaunchedEffect(key1 = true){
        uiEvent.collect {event ->
            when(event) {
                is UiEvent.PopBackStack -> navController.popBackStack()
                is UiEvent.Navigate -> navController.navigate(event.route)
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        val result =  snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                            duration = SnackbarDuration.Short
                        )
                        onResult(result)
                    }
                }
                is UiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}