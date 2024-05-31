package com.example.mvvmtodo.presenter.ui.common.alert_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAlertDialog(
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val h = this.minHeight
        val w = this.maxWidth

        androidx.compose.material3.BasicAlertDialog(onDismissRequest = { onDismissDialog() }) {
            Card(
                modifier = Modifier
                    .height(h)
                    .width(w),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = w.times(.15f), vertical = h.times(.16f)),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    title?.let { it() }

                    Spacer(modifier = Modifier.height(h.times(0.04f)))
                    content()

                    Spacer(modifier = Modifier.height(h.times(0.15f)))
                    buttons?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            it()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBasicAlertDialog(
    modifier: Modifier,
    title: String = "",
    message: String = "",
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    positiveClick: () -> Unit = {},
    negativeClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val h = this.minHeight
        val w = this.maxWidth

        CommonAlertDialog(
            onDismissDialog = { negativeClick() },
            modifier = Modifier
                .height(h)
                .width(w),
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = h.times(0.18f).value.sp,
                        letterSpacing = .5.sp
                    )
                )
            },
            buttons = {
                Text(
                    modifier = Modifier
                        .clickable {
                            negativeClick()
                        },
                    text = negativeButtonText,
                    style = TextStyle(
                        fontSize = h.times(0.15f).value.sp,
                    )
                )

                Spacer(modifier = Modifier.width(w.times(.5f)))

                Text(
                    modifier = Modifier
                        .clickable {
                            positiveClick()
                        },
                    text = positiveButtonText,
                    style = TextStyle(
                        fontSize = h.times(0.15f).value.sp,
                    )
                )
            }
        ) {
            Text(
                text = message,
                style = TextStyle(
                    fontSize = h.times(0.11f).value.sp,
                    fontWeight = FontWeight.W400,
                    letterSpacing = .5.sp
                )
            )
        }
    }
}