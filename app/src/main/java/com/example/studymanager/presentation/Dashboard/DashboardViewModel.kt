package com.example.studymanager.presentation.Dashboard

import androidx.lifecycle.ViewModel
import com.example.studymanager.doamin.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val subjectRepository: SubjectRepository) :
    ViewModel() {
}