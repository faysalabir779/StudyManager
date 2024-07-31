package com.example.studymanager.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow

enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW("Low", Green, 0),
    MEDIUM("Medium", Yellow, 1),
    HIGH("High", Red, 2);

    companion object {
        fun fromValue(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM

    }
}
