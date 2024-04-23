package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun EditStatusContent(
    isComplete: Boolean,
    onCompletedChange: ()-> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier,
            checked = isComplete,
            onCheckedChange = {
                onCompletedChange()
            })

        Text(
            text = "Mark as done",
            style = TextStyle(
                color = Color.White,
                fontSize = 17.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}