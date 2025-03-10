package com.example.studymanager.presentation.session

import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject

data class SessionState (
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)