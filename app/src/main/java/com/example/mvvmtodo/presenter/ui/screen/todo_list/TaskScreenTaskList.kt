package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.data.model.Todo
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenTaskList(
    taskList: List<Todo>,
    onEvent: (ToDoListContract.TodoListEvent) -> Unit
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2)
    )
    {
        items(taskList) { task ->
            val randomColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
            val randomHeight = Random.nextInt(100, 300)

            TodoItem(
                modifier = Modifier
                    .padding(5.dp)
                    .background(randomColor, RoundedCornerShape(15.dp))
                    .clickable {
                        onEvent(ToDoListContract.TodoListEvent.OnTodoItemClick(task))
                    },
                item = task,
                height = randomHeight.dp
            )
        }
    }
}