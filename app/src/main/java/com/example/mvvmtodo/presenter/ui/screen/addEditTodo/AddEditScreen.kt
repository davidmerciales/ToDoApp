package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.NavigationContext
import com.example.mvvmtodo.presenter.theme.Purple40
import com.example.mvvmtodo.presenter.ui.navigation.AppController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditScreen(
    state: AddEditContract.AddEditState,
    onEvent: (AddEditContract.AddEditEvent)-> Unit,
    onPopBackStack: () -> Unit,
    appController: AppController
) {
    NavigationContext(
        snackbarHostState = state.snackbarHostState,
        appController = appController
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Purple40
                    ),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Details",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onPopBackStack() }) {
                            Icon(
                                tint = Color.White,
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            content = {
                AddEditScreenContent(
                    paddingValues = it,
                    title = state.title,
                    description = state.description,
                    onTitleChange = { title ->
                        onEvent(AddEditContract.AddEditEvent.OnTitleChange(title))
                    },
                    onDescriptionChange = { description ->
                        onEvent(
                            AddEditContract.AddEditEvent.OnDescriptionChange(
                                description
                            )
                        )
                    },
                    isComplete = state.isDone,
                    onCompletedChange = {
                        onEvent(AddEditContract.AddEditEvent.OnCompletedChange)
                    }
                )
            },
            bottomBar = {
                BottomSaveButton {
                    onEvent(AddEditContract.AddEditEvent.OnSaveTodo)
                }
            }
        )
    }
}