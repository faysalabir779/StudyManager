package com.example.studymanager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studymanager.doamin.model.Subject

@Composable
fun AddSubjectDialogue(
    isOpen: Boolean,
    selectedColor: List<Color>,
    onColorChange: (List<Color>) -> Unit,
    subjectName: String,
    onSubjectChange: (String) -> Unit,
    goalHours: String,
    onGoalHoursChange: (String) -> Unit,
    onDismissClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    var focus = LocalFocusManager.current
    var subjectNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var goalsHourError by rememberSaveable { mutableStateOf<String?>(null) }

    subjectNameError = when {
        subjectName.isBlank() -> "Please Enter Subject Name"
        subjectName.length < 2 -> "Subject Name too short"
        subjectName.length > 20 -> "Subject Name too long"
        else -> null
    }

    goalsHourError = when {
        goalHours.isBlank() -> "Please Enter Goals Hour"
        goalHours.toFloatOrNull() == null -> "Invalid Number"
        goalHours.toFloat() < 1f -> "Please set at least 1 Hour"
        goalHours.toFloat() > 1000f -> "Please set a maximum of 1000 Hour"
        else -> null

    }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissClick,
            title = {
                Text(text = "Add/Update Subject")
            },
            text = {
                Column(modifier = Modifier.clickable(

                ) { focus.clearFocus() }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Subject.subjectCardColor.forEach { colors ->
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .border(
                                        width = 2.dp,
                                        color = if (colors == selectedColor) Color.Black
                                        else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .background(
                                        shape = CircleShape,
                                        brush = Brush.verticalGradient(colors)
                                    )
                                    .clip(CircleShape)
                                    .clickable
                                    { onColorChange(colors) },
                            )
                        }

                    }
                    OutlinedTextField(
                        value = subjectName, onValueChange = onSubjectChange,
                        label = {
                            Text(text = "Subject Name")
                        },
                        maxLines = 1,
                        supportingText = {
                            Text(text = subjectNameError.orEmpty())
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHours, onValueChange = onGoalHoursChange,
                        label = {
                            Text(text = "Goal Study Hours")
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = {
                            Text(text = goalsHourError.orEmpty())
                        }
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissClick) {
                    Text(text = "Cancel")
                }

            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = subjectNameError == null && goalsHourError == null
                ) {
                    Text(text = "Save")
                }
            },
            shape = RoundedCornerShape(15.dp)
        )
    }

}