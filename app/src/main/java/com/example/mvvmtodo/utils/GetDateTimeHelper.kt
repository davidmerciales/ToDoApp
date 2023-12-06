package com.example.mvvmtodo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetDateTimeHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")
        return currentDateTime.format(formatter)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")
    return this.format(formatter)
}