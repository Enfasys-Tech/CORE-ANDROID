package enfasys.android.core

import enfasys.android.core.usecase.Result

interface NotificationManager {
    fun clearAllNotifications()
    suspend fun getTokenNotification(): Result<String>
}