package com.example.mvvmtodo.presenter.ui.screen.addEditTodo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun EditStatusContent(
    isComplete: Boolean = true,
    progress: Float = 0f,
    onCompletedChange: ()-> Unit = {},
    onProgressChange: (Float)-> Unit ={},
    onProgressFinished: ()-> Unit = {}
) {
    Column (
        modifier = Modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(22.dp),
            verticalAlignment = Alignment.CenterVertically) {

            RadioButton(
                selected = isComplete,
                onClick = { onCompletedChange()})

            Text(
                text = if (!isComplete) "In progress" else "Completed",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 15.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }

        Row(modifier = Modifier
            .padding(horizontal = 13.dp),
            verticalAlignment = Alignment.CenterVertically) {

            LinearProgressIndicator(
                modifier = Modifier
                    .height(7.dp)
                    .weight(0.5f),
                    progress = progress)
            Text(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth(),
                text = "${(progress * 100).toInt()}%",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}