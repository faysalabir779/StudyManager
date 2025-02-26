package com.example.studymanager.presentation.session

import androidx.lifecycle.ViewModel
import com.example.studymanager.doamin.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(private val sessionRepository: SessionRepository) :
    ViewModel() {
}