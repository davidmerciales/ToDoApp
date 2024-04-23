package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import android.util.Log
import android.view.textclassifier.TextLinks.TextLink
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.data.model.Todo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenContent(
    padding: PaddingValues,
    state: ToDoListContract.ToDoListState,
    onEvent: (ToDoListContract.TodoListEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column {

            TakScreenContentHeader()

            Spacer(modifier = Modifier.height(10.dp))

            TaskScreenSearchBar(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                searchText = "Search",
                onQueryChange = {},
                onSearch = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            TaskScreenTaskProgress(
                completedTask = state.completedTask,
                totalTask = state.todos.size,
                completeTaskPercent = state.completedTaskPercentage
            ){
                onEvent(ToDoListContract.TodoListEvent.OnDateTodayClick)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier
                .fillMaxWidth(.98f),
                contentAlignment = Alignment.CenterEnd){
                Text(
                    modifier = Modifier
                        .clickable {
                            onEvent(ToDoListContract.TodoListEvent.OnCompletedNavClick)
                        },
                    text = "View completed",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.End
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TaskScreenTaskList(
                taskList = state.todos,
                onEvent = onEvent
            )
        }
    }
}