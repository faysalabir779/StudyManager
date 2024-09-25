package com.example.studymanager.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Common(val title: String, val color: Color, val value: Int) {
    LOW("Low", Green, 0), MEDIUM("Medium", Yellow, 1), HIGH("High", Red, 2);

    companion object {
        fun fromValue(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM

    }
}

fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}
