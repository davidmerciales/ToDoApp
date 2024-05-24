package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mvvmtodo.presenter.theme.Purple40
import com.example.mvvmtodo.presenter.ui.screen.todayTask.screen.CustomCalendar
import io.github.ronjunevaldoz.kmp_calendar.CalendarSelection
import io.github.ronjunevaldoz.kmp_calendar.rememberCalendarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreenContent(
    paddingValues: PaddingValues,
    state: AddEditContract.AddEditState,
    onEvent: (AddEditContract.AddEditEvent)-> Unit,
) {
    val scope = rememberCoroutineScope()
    val timePickerState = rememberTimePickerState()
    val calendarState = rememberCalendarState(selection = CalendarSelection.Single,)
    val sheetState = rememberModalBottomSheetState()

    var showTimePicker by remember { mutableStateOf(false) }
    var isDatePickerShow by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
                onSetTime = {showTimePicker = !showTimePicker},
                onSetDate = {isDatePickerShow = !isDatePickerShow}
            )

            Spacer(modifier = Modifier.height(10.dp))

            EditDescriptionContent(description = state.description, onDescriptionChange = { onEvent(AddEditContract.AddEditEvent.OnDescriptionChange(it)) })

            Spacer(modifier = Modifier.height(20.dp))

            EditStatusContent(
                isComplete = state.isDone,
                progress = state.progress,
                onCompletedChange = { onEvent(AddEditContract.AddEditEvent.OnCompletedChange) },
                onProgressChange = { onEvent(AddEditContract.AddEditEvent.OnProgressChange(it))},
                onProgressFinished = { onEvent(AddEditContract.AddEditEvent.OnProgressFinished)}
                )
            
            Text(modifier = Modifier
                .padding(15.dp),
                text = "Sub task")

            LazyColumn{
                items(1){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 4.dp)
                        .border(2.dp, color = Color.Black.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .clickable {
                            showBottomSheet = !showBottomSheet
                        }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Text(text = "Add sub task ")

                            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                        }
                    }

                }
                itemsIndexed(state.subtasks){index,subTask->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically) {

                            Checkbox(checked = subTask.isDone, onCheckedChange = {onEvent(AddEditContract.AddEditEvent.OnSubTaskCompletedChange(state.isSubtaskDone,subTask.subTaskID?:-1))})

                            Text(text = subTask.description)
                        }
                    }
                }
            }

            if(showBottomSheet){
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Column {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            value = state.subtaskDescription,
                            placeholder = { Text(text = "Enter sub task") },
                            onValueChange = {onEvent(AddEditContract.AddEditEvent.OnSubTaskDescriptionChange(it))})

                        Spacer(modifier = Modifier.height(20.dp))

                        BottomSaveButton(
                            title = "Add",
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.4f)
                                .padding(horizontal = 10.dp)
                                .background(Purple40, RoundedCornerShape(15.dp))) {
                            onEvent(AddEditContract.AddEditEvent.OnSaveSubTask)
                            showBottomSheet = false
                        }
                    }
                }
            }

            if (isDatePickerShow){
                Dialog(onDismissRequest = {
                    isDatePickerShow = !isDatePickerShow
                }, DialogProperties(usePlatformDefaultWidth = false)) {
                    Card(
                        modifier = Modifier
                            .width(230.dp)
                            .wrapContentHeight(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        CustomCalendar(calendarState = calendarState) {
                            //selectedDate = it.date.toLocalDate("MMMM dd")
                            isDatePickerShow = !isDatePickerShow
                        }
                    }
                }
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
}