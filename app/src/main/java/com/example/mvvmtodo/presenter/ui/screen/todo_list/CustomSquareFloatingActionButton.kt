package com.example.mvvmtodo.presenter.ui.screen.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmtodo.presenter.theme.Purple40

@Composable
fun CustomSquareFloatingActionButton(
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clickable { onClick() }
        .background(Purple40, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center) {

        Row(
            modifier = Modifier
                .padding(horizontal = 60.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Add new task",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )

            Icon(
                tint = Color.White,
                imageVector = Icons.Default.Add,
                contentDescription = "add task"
            )
        }
    }
}