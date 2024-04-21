package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.presenter.theme.Purple40

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(it)
                    .wrapContentSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Purple40,
                                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                            .fillMaxHeight(0.18f)
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .offset(y = (10).dp)
                                .zIndex(1f),
                            text = "Task title",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 13.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            ),
                            value = viewModel.state.title,
                            onValueChange = { title ->
                                viewModel.state.title = title
                            },
                            placeholder = {
                                Text(
                                    text = "Enter Title",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W500,
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    )
                                )
                            },
                            singleLine = false,
                            maxLines = 5
                        )

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .offset(y = (10).dp)
                                .zIndex(1f),
                            text = "Due Date",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 13.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .offset(y = (10).dp)
                            .zIndex(1f),
                        text = "Descriptions",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(0.92f),
                            value = viewModel.state.description,
                            onValueChange = { description ->
                                viewModel.state.description = description
                            },
                            shape = RoundedCornerShape(10.dp),
                            label = { Text(text = "Enter desciption") },
                            singleLine = false,
                            minLines = 5,
                            maxLines = 5
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            modifier = Modifier,
                            checked = viewModel.state.isDone,
                            onCheckedChange = {
                                viewModel.state.isDone = !viewModel.state.isDone
                            })

                        Text(
                            text = "Mark as done",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 17.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.075f)
                    .padding(horizontal = 15.dp)
                    .background(Purple40, RoundedCornerShape(15.dp))
                    .clickable {
                        viewModel.OnEvent(AddEditContract.AddEditEvent.OnSaveTodo)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    )
}
