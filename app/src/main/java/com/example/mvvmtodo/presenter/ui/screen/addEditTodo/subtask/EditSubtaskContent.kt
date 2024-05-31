package com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvmtodo.data.model.Subtask

@Composable
fun EditSubtaskContent(
    subtasks: List<Subtask>,
    onAddSubtask: ()-> Unit,
    onSelectSubtask: (Int, String)-> Unit,
    onSubTaskCompletedChange: (Subtask)-> Unit
){
    Text(
        modifier = Modifier
            .padding(15.dp),
        text = "Sub task"
    )

    LazyColumn {
        item {
            AddSubtaskItem {
                onAddSubtask()
            }
        }
        items(subtasks) { subTask ->
            SubtaskItem(
                subTask = subTask,
                isSubtaskDone = subTask.isDone,
                onSelectSubtask = {
                    onSelectSubtask((subTask.subTaskID ?: -1).toInt(), subTask.description)
                },
                onSubTaskCompletedChange = {
                    Log.d("EditSubtaskContent: ", subTask.subTaskID.toString())
                    onSubTaskCompletedChange(subTask)
                })
        }
    }
}