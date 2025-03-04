package com.example.studymanager.presentation.session

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.example.studymanager.MainActivity
import com.example.studymanager.util.Constants.CLICK_REQUEST_CODE

object ServiceHelper {

    //clicking on notification to the screen
    fun clickPendingIntent(context: Context): PendingIntent {

        //since we have to go to a particular screen so it is deppLinking
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "study_manager://dashboard/session".toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(
                CLICK_REQUEST_CODE,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    fun triggeredForegroundService(context: Context, action: String) {
        Intent(context, StudySessionTimerService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}