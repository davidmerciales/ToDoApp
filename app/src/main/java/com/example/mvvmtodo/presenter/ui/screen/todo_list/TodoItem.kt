package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.utils.priorityToColor
import com.example.mvvmtodo.utils.priorityToString
import com.example.mvvmtodo.utils.stringToDate
import com.example.mvvmtodo.utils.stringToDateMonthDay

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (ToDoListContract.TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val color = if (todo.isDone) Color.Gray else todo.priority.priorityToColor()

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.3f),
        ),
        border = BorderStroke(1.dp, color),
        onClick = {
            onEvent(ToDoListContract.TodoListEvent.OnTodoItemClick(todo))
        }
    ) {
        ConstraintLayout {
            val (txtDate, txtPriority, txtTitle, deleteIcon, txtDescription, checkBox) = createRefs()
            Text(
                modifier = Modifier.constrainAs(txtDate) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                },
                text = todo.date,
                fontSize = 10.sp
            )
            Text(
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                },
                text = todo.priority.priorityToString(),
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .constrainAs(txtTitle) {
                        top.linkTo(txtDate.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                    },
                text = todo.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(.2f)
                    .constrainAs(deleteIcon) {
                        top.linkTo(txtDate.bottom)
                        start.linkTo(txtTitle.end, margin = 20.dp)
                    },
                onClick = {
                    onEvent(ToDoListContract.TodoListEvent.OnDeleteTodo(todo))
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .constrainAs(txtDescription) {
                        top.linkTo(txtTitle.bottom)
                        start.linkTo(parent.start, margin = 10.dp)
                    },
                text = todo.description
            )

            Checkbox(
                modifier = Modifier
                    .fillMaxWidth(.1f)
                    .constrainAs(checkBox) {
                        top.linkTo(txtDate.bottom)
                        start.linkTo(parent.end)
                    },
                checked = todo.isDone,
                onCheckedChange = {
                    onEvent(ToDoListContract.TodoListEvent.OnDoneTodo(todo, it))
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    height: Dp,
    item: Todo
) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = item.title,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = item.description,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }

        Spacer(modifier = Modifier.height(height))
        val date = item.date.stringToDate().stringToDateMonthDay()
        Text(
            text = "Due: $date", style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}