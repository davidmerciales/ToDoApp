package com.example.mvvmtodo.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun Int.priorityToColor() : Color {
    return when(this) {
        3-> Color("#CB4335".toColorInt())
        2 -> Color("#D35400".toColorInt())
        1 -> Color("#E67E22".toColorInt())
        0 -> Color("#F4D03F".toColorInt())
        else -> {Color("#F4D03F".toColorInt())}
    }
}