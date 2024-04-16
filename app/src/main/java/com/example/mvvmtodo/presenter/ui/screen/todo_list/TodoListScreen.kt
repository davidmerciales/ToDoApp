package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.presenter.theme.Pink80
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TaskManagementScreen(
    viewModel: TodoListViewModel = hiltViewModel()
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
            CustomSquareFloatingActionButton {
                viewModel.onEvent(ToDoListContract.TodoListEvent.OnAddEditTodo)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Content(viewModel.state.todos, viewModel::onEvent)
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    list: List<Todo>,
    onEvent: (ToDoListContract.TodoListEvent) -> Unit
) {
    Column {

        Column(
            modifier = Modifier
                .padding(start = 18.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "Hi, User!",
                style = TextStyle(
                    fontSize = 16.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
            Text(
                text = "Be productive today",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            query = "Search",
            onQueryChange = { },
            onSearch = { },
            active = false,
            onActiveChange = { },
            shape = RoundedCornerShape(20.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color.LightGray
            ),
            placeholder = { Text(text = "Search") },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { },
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            //List
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .background(Color.LightGray, RoundedCornerShape(10.dp))
        ) {

            Row(
                modifier = Modifier.padding(
                    start = 25.dp,
                    end = 25.dp,
                    top = 20.dp,
                    bottom = 20.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Task Progress",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W700,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Text(
                        text = "30/40 task done",
                        style = TextStyle(
                            fontSize = 16.sp,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Box(
                        modifier = Modifier
                            .background(Pink80, RoundedCornerShape(20.dp))
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 15.dp),
                            text = "March 22",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }
                }
                Text(
                    text = "80%",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W600,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2)
        )
        {
            items(list) { todo ->
                val randomColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
                val randomHeight = Random.nextInt(100, 300)

                TodoItem(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(randomColor, RoundedCornerShape(15.dp))
                        .clickable {
                            onEvent(ToDoListContract.TodoListEvent.OnTodoItemClick(todo))
                        },
                    item = todo,
                    height = randomHeight.dp
                )
            }
        }
    }
}

