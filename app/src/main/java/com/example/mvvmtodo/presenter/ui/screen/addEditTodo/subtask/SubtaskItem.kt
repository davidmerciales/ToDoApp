package com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.data.model.Subtask

@Composable
fun SubtaskItem(
    subTask: Subtask,
    isSubtaskDone: Boolean,
    onSelectSubtask: ()-> Unit,
    onSubTaskCompletedChange: ()-> Unit,
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 4.dp)
        .clickable {
            onSelectSubtask()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = if (subTask.subTaskID != null) subTask.isDone else isSubtaskDone,
                onCheckedChange = {
                    onSubTaskCompletedChange()
                })

            Text(text = subTask.description)
        }
    }
}