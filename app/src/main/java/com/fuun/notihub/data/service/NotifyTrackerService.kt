package com.fuun.notihub.data.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.fuun.notihub.data.preferences.PreferencesStore
import com.fuun.notihub.domain.entities.Notify
import com.fuun.notihub.domain.repository.NotifyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

class NotifyTrackerService : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val repository: NotifyRepository by inject()
    private val preferencesStore: PreferencesStore by inject()

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        handleNotification(sbn)
    }

    private fun handleNotification(sbn: StatusBarNotification?) {
        if (!preferencesStore.notificationTrackerEnable) return

        Timber.d("notification: ${sbn.toString()}")
        scope.launch {
            checkNotNull(sbn)
            if (sbn.notification.tickerText != null) {
                repository.storeNotify(
                    key = sbn.key,
                    appPackage = sbn.opPkg,
                    text = sbn.notification.tickerText?.toString() ?: "unknown",
                    date = sbn.postTime
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
