package com.example.mvvmtodo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaInstant
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class GetDateTimeHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(pattern: String): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return currentDateTime.format(formatter)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")
    return this.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.stringToDate(pattern: String): LocalDateTime {
    val dateFormat = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(this, dateFormat)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.stringToDateMonthDay(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(formatter)
}