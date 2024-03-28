package com.example.mvvmtodo.presenter.ui.screen.completedTodo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.CollectMessages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedToDoScreen(
    viewModel: CompletedToDoViewModel = hiltViewModel(),
    appController: AppController
) {

    appController.CollectMessages(snackbarHostState = viewModel.state.snackbarHostState) { result ->
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.onEvent(CompletedToDoContract.CompletedToEvent.OnUndoDelete)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = "Completed",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(.5f)
                        .clickable { viewModel.onEvent(CompletedToDoContract.CompletedToEvent.OnToDoNavClick) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "To Do",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(.5f)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Completed",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(viewModel.state.snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("OnAddClick", "TodoListScreen: ")
                    viewModel.onEvent(CompletedToDoContract.CompletedToEvent.OnAddEditTodo)
                }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(viewModel.state.todos) {
                        if (it.isDone) {
                            CompletedToDoItem(
                                todo = it,
                                onEvent = viewModel::onEvent,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

