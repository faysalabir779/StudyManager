package com.example.studymanager.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW("Low", Color(0xFF1AAA55), 0), MEDIUM("Medium", Color(0xFFFC9403), 1), HIGH("High", Color(0xFFDB3B21), 2);

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

fun Long.toHours(): Float {
    val hours = this.toFloat() / 3600f
    return "%.2f".format(hours).toFloat()
}


sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackBarEvent()

    data object NavigateUp : SnackBarEvent()
}

fun Int.pad(): String {
    return this.toString().padStart(length = 2, padChar = '0')
}