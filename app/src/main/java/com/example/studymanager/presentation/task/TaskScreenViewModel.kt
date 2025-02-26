package com.example.studymanager.presentation.task

import androidx.lifecycle.ViewModel
import com.example.studymanager.doamin.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
}