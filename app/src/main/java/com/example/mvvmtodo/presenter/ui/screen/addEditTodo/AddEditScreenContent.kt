package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mvvmtodo.data.model.Subtask
import com.example.mvvmtodo.presenter.ui.common.alert_dialog.CustomBasicAlertDialog
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask.AddSubtaskBottomSheet
import com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask.EditSubtaskContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreenContent(
    paddingValues: PaddingValues,
    state: AddEditContract.AddEditState,
    onEvent: (AddEditContract.AddEditEvent) -> Unit,
) {
    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }
    var isDatePickerShow by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var isEditSubtask by remember { mutableStateOf(false) }
    var selectedSubtask by remember { mutableIntStateOf(-1) }
    var isAlertDialogShow by remember { mutableStateOf(false) }

    if (isAlertDialogShow) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val h = this.minHeight
            val w = this.maxWidth

            CustomBasicAlertDialog(
                modifier = Modifier
                    .zIndex(1f)
                    .width(w.times(.18f))
                    .height(h.times(0.15f)),
                title = "Delete",
                message = "Are you sure you want to delete this subtask?",
                positiveButtonText = "Yes",
                negativeButtonText = "No",
                positiveClick = {
                    isAlertDialogShow = false
                    showBottomSheet = false
                    onEvent(AddEditContract.AddEditEvent.OnDeleteSubtask(selectedSubtask))
                },
                negativeClick = {
                    isAlertDialogShow = false
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .wrapContentSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            EditTitleContent(
                title = state.title,
                onTitleChange = { onEvent(AddEditContract.AddEditEvent.OnTitleChange(it)) },
                onSetTime = { showTimePicker = !showTimePicker },
                onSetDate = { isDatePickerShow = !isDatePickerShow }
            )

            Spacer(modifier = Modifier.height(10.dp))

            EditDescriptionContent(
                description = state.description,
                onDescriptionChange = { onEvent(AddEditContract.AddEditEvent.OnDescriptionChange(it)) })

            Spacer(modifier = Modifier.height(20.dp))

            EditStatusContent(
                isComplete = state.isDone,
                progress = state.progress,
                onCompletedChange = { onEvent(AddEditContract.AddEditEvent.OnCompletedChange) },
                onProgressChange = { onEvent(AddEditContract.AddEditEvent.OnProgressChange(it)) },
                onProgressFinished = { onEvent(AddEditContract.AddEditEvent.OnProgressFinished) }
            )

            EditSubtaskContent(
                subtasks = state.subtasks,
                onAddSubtask = { showBottomSheet = !showBottomSheet},
                onSelectSubtask = {subtask, description ->
                    selectedSubtask = subtask
                    isEditSubtask = true
                    showBottomSheet = true
                    state.subtaskDescription = description
                },
                onSubTaskCompletedChange = {subtask->
                    if (subtask.subTaskID != null) {
                        onEvent(
                            AddEditContract.AddEditEvent.OnSubTaskCompletedChange(
                                !subtask.isDone,
                                subtask.subTaskID ?: -1
                            )
                        )
                    } else {
                        state.isSubtaskDone = !state.isSubtaskDone
                    }
                }
            )
        }

        if (showBottomSheet) {
            AddSubtaskBottomSheet(
                subtaskDescription = state.subtaskDescription,
                isEditSubtask = isEditSubtask,
                onDismissRequest = {
                    showBottomSheet = false
                },
                onKeyboardActionDone = {
                    onEvent(AddEditContract.AddEditEvent.OnSaveSubTask(selectedSubtask))
                    showBottomSheet = false
                },
                onSubtaskDescriptionChange = {
                    onEvent(AddEditContract.AddEditEvent.OnSubTaskDescriptionChange(it))
                },
                onSaveSubtask = {
                    onEvent(AddEditContract.AddEditEvent.OnSaveSubTask(selectedSubtask))
                    showBottomSheet = false
                },
                onDeleteSubtask = { isAlertDialogShow = true }
            )
        }

        if (isDatePickerShow) {
            AddEditScreenCalendar(
                onDismiss = { isDatePickerShow = !isDatePickerShow }
            )
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = {
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                        }
                    ) { Text("OK") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                        }
                    ) { Text("Cancel") }
                }) {
                TimePicker(state = timePickerState)
            }
        }
    }
}

