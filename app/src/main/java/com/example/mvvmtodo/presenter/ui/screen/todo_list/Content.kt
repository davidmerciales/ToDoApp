package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.data.model.Todo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    padding: PaddingValues
    state: ToDoListContract.ToDoListState,
    list: List<Todo>,
    onEvent: (ToDoListContract.TodoListEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column {

            TakScreenContentHeader()

            Spacer(modifier = Modifier.height(20.dp))

            TaskScreenSearchBar(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                searchText = "Search",
                onQueryChange = {},
                onSearch = {}
            )

            Spacer(modifier = Modifier.height(15.dp))

            TaskScreenTaskProgress(
                completedTask = state.completedTask,
                totalTask = state.todos.size,
                completeTaskPercent = state.completedTaskPercentage
            )

            Spacer(modifier = Modifier.height(15.dp))

            TaskScreenTaskList(
                list,
                onEvent
            )
        }
    }
}