package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.presenter.ui.navigation.AppController
import com.example.mvvmtodo.presenter.ui.navigation.CollectMessages
import com.example.mvvmtodo.utils.priorityToInt

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel(),
    appController: AppController
) {

    appController.CollectMessages(snackbarHostState = viewModel.state.snackbarHostState) {
        Log.d("OnDoneClick", "AddEditScreen: ")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Create New ToDo",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.OnEvent(AddEditContract.AddEditEvent.OnPriorityChange(viewModel.state.selectedText.priorityToInt()))
                    viewModel.OnEvent(AddEditContract.AddEditEvent.OnSaveTodo)
                }) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ExposedDropdownMenuBox(
                        expanded = viewModel.state.expanded,
                        onExpandedChange = {
                            viewModel.state.expanded = !viewModel.state.expanded
                        }
                    ) {
                        TextField(
                            value = viewModel.state.selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.state.expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = viewModel.state.expanded,
                            onDismissRequest = { viewModel.state.expanded = false }
                        ) {
                            viewModel.state.priorities.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        viewModel.state.selectedText = item
                                        viewModel.state.expanded = false
                                        viewModel.OnEvent(
                                            AddEditContract.AddEditEvent.OnPriorityChange(
                                                viewModel.state.selectedText.priorityToInt()
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp),
                    value = viewModel.state.title,
                    onValueChange = {
                        viewModel.OnEvent(AddEditContract.AddEditEvent.OnTitleChange(it))
                    },
                    placeholder = { Text(text = "Title") }
                )
                Spacer(modifier = Modifier.padding(6.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp),
                    value = viewModel.state.description,
                    onValueChange = {
                        viewModel.OnEvent(AddEditContract.AddEditEvent.OnDescriptionChange(it))
                    },
                    placeholder = { Text(text = "Description") },
                    singleLine = false,
                    maxLines = 5
                )
            }
        }
    )
}