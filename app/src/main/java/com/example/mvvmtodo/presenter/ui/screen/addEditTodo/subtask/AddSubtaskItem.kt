package com.example.mvvmtodo.presenter.ui.screen.addEditTodo.subtask

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddSubtaskItem(
    onClick: ()-> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 4.dp)
        .border(
            2.dp,
            color = Color.Black.copy(alpha = 0.5f),
            RoundedCornerShape(10.dp)
        )
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Add sub task ")

            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
        }
    }
}