package com.example.mvvmtodo.presenter.ui.screen.todo_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import com.example.mvvmtodo.presenter.theme.Purple40
import com.example.mvvmtodo.utils.GetDateTimeHelper

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreenTaskProgress(
    completedTask: Int,
    totalTask: Int,
    completeTaskPercent: Double,
    onClick: ()-> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .background(Color.LightGray, RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Task Progress",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                Text(
                    text = "${completedTask}/${totalTask}",
                    style = TextStyle(
                        fontSize = 13.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )

                Spacer(modifier = Modifier.height(2.dp))

                Box(
                    modifier = Modifier
                        .background(Purple40, RoundedCornerShape(20.dp))
                        .clickable {
                            onClick()
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 3.dp, horizontal = 10.dp),
                        text = GetDateTimeHelper().getCurrentDateTime("MMM dd"),
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
            Text(
                text = "${completeTaskPercent.toInt()}%",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
    }
}