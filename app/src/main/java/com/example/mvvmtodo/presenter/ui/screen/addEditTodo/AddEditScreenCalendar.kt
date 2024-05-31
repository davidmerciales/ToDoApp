package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mvvmtodo.presenter.ui.screen.todayTask.screen.CustomCalendar
import io.github.ronjunevaldoz.kmp_calendar.CalendarSelection
import io.github.ronjunevaldoz.kmp_calendar.rememberCalendarState

@Composable
fun AddEditScreenCalendar(
    onDismiss: ()-> Unit
){
    val calendarState = rememberCalendarState(selection = CalendarSelection.Single)

    Dialog(onDismissRequest = {
        onDismiss()
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
                onDismiss()
            }
        }
    }
}