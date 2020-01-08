package com.enfasys.core

import com.enfasys.core.usecase.Result

interface NotificationManager {
    fun clearAllNotifications()
    suspend fun getTokenNotification(): Result<String>
}