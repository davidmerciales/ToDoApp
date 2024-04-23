package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.mvvmtodo.presenter.theme.Purple40

@Composable
fun EditTitleContent(
    title: String,
    onTitleChange: (String)-> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                Purple40,
                RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            )
            .fillMaxHeight(0.18f)
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .offset(y = (10).dp)
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
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            value = title,
            onValueChange = { title ->
                onTitleChange(title)
            },
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
            maxLines = 5
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
    }
}