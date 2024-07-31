package com.example.studymanager.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studymanager.R

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectName: String,
    subjectColor: List<Color>,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                brush = Brush.verticalGradient(subjectColor),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick }) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.book2),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
            Text(text = subjectName, style = MaterialTheme.typography.headlineSmall, color = Color.White)
        }
    }
}