package com.example.mvvmtodo.utils

fun Int.priorityToString(): String {
    return when (this) {
        3 -> "Critical"
        2 -> "High"
        1 -> "Medium"
        0 -> "Low"
        else -> {
            "Low"
        }
    }
}

fun String.priorityToInt(): Int {
    return when (this) {
        "Critical" -> 3
        "High" -> 2
        "Medium" -> 1
        "Low" -> 0
        else -> {
            0
        }
    }
}