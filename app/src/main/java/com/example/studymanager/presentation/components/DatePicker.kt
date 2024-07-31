package com.example.studymanager.presentation.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmClick : () -> Unit,
) {
    if (isOpen){
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmClick) {
                    Text(text = "OK")
                }
            },
            dismissButton ={
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            content = {
                androidx.compose.material3.DatePicker(
                    state = state,
                )
            }
        )
    }
}