package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun EditDescriptionContent(
    description: String,
    onDescriptionChange: (String)-> Unit
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .offset(y = (10).dp)
            .zIndex(1f),
        text = "Descriptions",
        style = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.92f),
            value = description,
            onValueChange = { description ->
                onDescriptionChange(description)
            },
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = "Enter description") },
            singleLine = false,
            minLines = 5,
            maxLines = 5
        )
    }
}