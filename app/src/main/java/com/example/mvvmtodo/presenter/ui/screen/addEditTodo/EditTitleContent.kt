package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.mvvmtodo.presenter.theme.Purple40

@Composable
@Preview
fun EditTitleContent(
    title: String = "Title",
    onTitleChange: (String)-> Unit = {},
    onSetTime: ()-> Unit,
    onSetDate: ()-> Unit
) {
    Column(
        modifier = Modifier
            .background(
                Purple40,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
            .fillMaxHeight(0.25f)
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                //.offset(y = (10).dp)
                .zIndex(1f),
            text = "Task title",
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.White.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            value = title,
            onValueChange = { title ->
                onTitleChange(title)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            placeholder = {
                Text(
                    text = "Enter Title",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            },
            singleLine = false,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .offset(y = (10).dp)
                .zIndex(1f),
            text = "Due Date",
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Alarm, tint = Color.White, contentDescription = "dueTime")
            }

            Text(
                modifier = Modifier
                    .clickable {
                        onSetTime()
                    },
                text = "10 am",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ))

            Spacer(modifier = Modifier.width(40.dp))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.CalendarMonth, tint = Color.White, contentDescription = "dueDate")
            }

            Text(
                modifier = Modifier
                    .clickable { onSetDate() },
                text = "April 25",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ))
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}