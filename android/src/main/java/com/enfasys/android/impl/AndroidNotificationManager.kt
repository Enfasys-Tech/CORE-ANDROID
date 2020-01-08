package com.enfasys.android.impl

import android.content.Context
import com.enfasys.core.Logger
import com.enfasys.core.NotificationManager
import com.enfasys.core.usecase.NoTokenAvailableError
import com.enfasys.core.usecase.Result
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AndroidNotificationManager @Inject constructor(
    private val logger: Logger,
    private val appContext: Context
) : NotificationManager {
    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

    override fun clearAllNotifications() {
        notificationManager.cancelAll()
    }

    override suspend fun getTokenNotification(): Result<String> {
        return try {
            val app = FirebaseApp.initializeApp(appContext)
            if (app != null) {
                val instance = FirebaseInstanceId.getInstance(app).instanceId.await()
                Result.success(instance.token)
            } else {
                logger.e(NoTokenAvailableError)
                Result.fail(NoTokenAvailableError)
            }
        } catch (ex: Exception) {
            logger.e(ex)
            logger.e(NoTokenAvailableError)
            Result.fail(NoTokenAvailableError)
        }
    }

}