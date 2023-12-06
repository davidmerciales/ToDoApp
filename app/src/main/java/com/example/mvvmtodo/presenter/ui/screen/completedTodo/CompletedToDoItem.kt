package com.example.mvvmtodo.presenter.ui.screen.completedTodo

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.utils.priorityToColor
import com.example.mvvmtodo.utils.priorityToString

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedToDoItem(
    todo: Todo,
    onEvent: (CompletedToDoContract.CompletedToEvent) -> Unit,
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
        border = BorderStroke(1.dp, color)
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
                    onEvent(CompletedToDoContract.CompletedToEvent.OnDeleteTodo(todo))
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
                    onEvent(CompletedToDoContract.CompletedToEvent.OnDoneTodo(todo, it))
                }
            )
        }
    }
}