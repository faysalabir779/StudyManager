package com.example.studymanager.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DeleteDialogue(
    isOpen: Boolean,
    title: String,
    bodyText: String,
    onDismissClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {


    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissClick,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = bodyText)
            },
            dismissButton = {
                TextButton(onClick = onDismissClick) {
                    Text(text = "Cancel")
                }

            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                ) {
                    Text(text = "Delete")
                }
            },
            shape = RoundedCornerShape(15.dp)
        )
    }

}