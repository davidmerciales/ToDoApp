package com.example.mvvmtodo.presenter.ui.screen.todayTask.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mvvmtodo.data.model.Todo
import com.example.mvvmtodo.presenter.theme.Black
import com.example.mvvmtodo.presenter.ui.screen.todayTask.viewmodel.TodayTaskContract
import com.example.mvvmtodo.utils.GetDateTimeHelper
import io.github.ronjunevaldoz.kmp_calendar.Calendar
import io.github.ronjunevaldoz.kmp_calendar.CalendarDay
import io.github.ronjunevaldoz.kmp_calendar.CalendarDefaults
import io.github.ronjunevaldoz.kmp_calendar.CalendarSelection
import io.github.ronjunevaldoz.kmp_calendar.CalendarState
import io.github.ronjunevaldoz.kmp_calendar.DefaultCalendarDayItem
import io.github.ronjunevaldoz.kmp_calendar.rememberCalendarState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayTaskScreen(
    state:  TodayTaskContract.TodayTaskState,
    onEvent: (TodayTaskContract.TodayTaskEvent)-> Unit
) {
    val test = GetDateTimeHelper().getCurrentDateTime("MMMM dd")
    val test2 = GetDateTimeHelper().getCurrentDateTime("MMMM dd yyyy")
    val yesterday = LocalDate.parse(test2, DateTimeFormatter.ofPattern("MMMM dd yyyy")).minusDays(1)
    val yesterdayFormatted = yesterday.format(DateTimeFormatter.ofPattern("MMMM dd"))

    var isDatePickerShow by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(GetDateTimeHelper().getCurrentDateTime("MMMM dd"))
    }

    val calendarState = rememberCalendarState(
        selection = CalendarSelection.Single,
    )

    val timezone = TimeZone.currentSystemDefault()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .clickable {
                               isDatePickerShow = true
                            },
                        text = selectedDate,
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onEvent(TodayTaskContract.TodayTaskEvent.OnBackClick)}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        floatingActionButton = {
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
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
                            selectedDate = it.date.toLocalDate("MMMM dd")
                            isDatePickerShow = !isDatePickerShow
                        }
                    }
                }
            }
            Content(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 15.dp),
                state.taskToday,
                text = when(selectedDate) {
                    test -> "Today's task"
                    yesterdayFormatted -> "Yesterday's task"
                    else -> "${selectedDate}'s task"
                }
            )
        }
    )
}
@Composable
fun CustomCalendar(calendarState: CalendarState,
                   onDateChange: (CalendarDay)->Unit) {
    Calendar(
        modifier = Modifier.padding(vertical = 12.dp),
        state = calendarState,
        colors = CalendarDefaults.calendarColors(),
        weekHeader = {
            Text(text = "${it.name.first()}")
        },
        calendarDay = { day, inRange, onDaySelected ->
            DefaultCalendarDayItem(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onDaySelected(day)
                        onDateChange(day)
                    },
                inRange = inRange,
                start = null,
                end = null,
                colors = CalendarDefaults.calendarColors(),
                selectedDate = calendarState.selected,
                day = day,
            )
        }
    )
}
@Composable
fun Content(
    modifier: Modifier = Modifier,
    list: List<Todo>,
    text: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.W500
            )
        )


        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(list) { task ->
                TodayTaskItem(task)

                Divider()
            }
        }
    }
}

@Composable
fun TodayTaskItem(
    task: Todo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .weight(0.15f),
            text = "10 AM"
        )

        Card(
            modifier = Modifier
                .weight(0.6f)
                .padding(start = 15.dp, top = 15.dp, bottom = 15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Black,
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    text = task.title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700
                    )
                )

                Text(
                    text = "10 am - 1 pm",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                )
                Text(
                    text = task.dateCreated,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300
                    )
                )
            }

        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
private fun kotlinx.datetime.LocalDate.toLocalDate(pattern: String): String {
    return this.toJavaLocalDate().format(DateTimeFormatter.ofPattern(pattern))
}