package com.example.mvvmtodo.presenter.ui.screen.todo_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun TakScreenContentHeader() {
    Column(
        modifier = Modifier
            .padding(start = 18.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = "Hi, User!",
            style = TextStyle(
                fontSize = 14.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        Text(
            text = "Be productive today",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}