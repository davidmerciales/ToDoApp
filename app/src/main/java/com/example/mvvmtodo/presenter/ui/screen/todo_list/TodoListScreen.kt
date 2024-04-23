package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagementScreen(
    state: ToDoListContract.ToDoListState,
    onEvent: (ToDoListContract.TodoListEvent)-> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {

                    IconButton(
                        onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "notification"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            CustomSquareFloatingActionButton("Add new task") {
                onEvent(ToDoListContract.TodoListEvent.OnAddEditTodo)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            TaskScreenContent(
                padding = padding,
                state = state,
                onEvent = onEvent
            )
        }
    )
}

