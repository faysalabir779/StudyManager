package com.example.studymanager.presentation.session

import com.example.studymanager.doamin.model.Session
import com.example.studymanager.doamin.model.Subject

sealed class SessionEvents {

    data class OnRelatedSubjectChange(val subject: Subject) : SessionEvents()

    data class SaveSession(val duration: Long) : SessionEvents()

    data class OnDeleteSessionButtonClick(val session: Session) : SessionEvents()

    data object DeleteSession : SessionEvents()

    data object NotifySubjectId : SessionEvents()

    data class UpdateSubjectIdAndRelatedSubject(
        val subjectId: Int?,
        val relatedToSubject: String?
    ) : SessionEvents()

}