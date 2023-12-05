package com.example.mvvmtodo.ui.screen.addEditTodo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.utils.UiEvent
import com.example.mvvmtodo.utils.priorityToInt

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    onPopBackStack:()->Unit,
    viewModel: AddEditViewModel = hiltViewModel()
){

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val priorities = arrayOf("Critical", "High", "Medium", "Low")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select Priority")}

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
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
                    IconButton(onClick = {onPopBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.OnEvent(AddEditEvent.OnPriorityChange(selectedText.priorityToInt()))
                    viewModel.OnEvent(AddEditEvent.OnSaveTodo)
                }) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Save")
            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            priorities.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedText = item
                                        expanded = false
                                        viewModel.OnEvent(AddEditEvent.OnPriorityChange(selectedText.priorityToInt()))
                                    }
                                )
                            }
                        }
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp, 0.dp, 50.dp, 0.dp),
                    value = viewModel.title,
                    onValueChange = {
                        viewModel.OnEvent(AddEditEvent.OnTitleChange(it))
                    },
                    placeholder = { Text(text = "Title")}
                )
                Spacer(modifier = Modifier.padding(6.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp, 0.dp, 50.dp, 0.dp),
                    value = viewModel.description,
                    onValueChange = {
                        viewModel.OnEvent(AddEditEvent.OnDescriptionChange(it))
                    },
                    placeholder = { Text(text = "Description")},
                    singleLine = false,
                    maxLines = 5
                )
            }
        }

    )
}