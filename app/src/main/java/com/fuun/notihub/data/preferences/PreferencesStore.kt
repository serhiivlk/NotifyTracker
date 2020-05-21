package com.fuun.notihub.data.preferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PreferencesStore constructor(
    private val context: Context
) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var notificationTrackerEnable: Boolean
        get() = prefs.getBoolean(NOTIFICATION_TRACKER_ENABLE, false)
        set(value) {
            prefs.edit {
                putBoolean(NOTIFICATION_TRACKER_ENABLE, value)
            }
        }

    companion object {
        private const val NOTIFICATION_TRACKER_ENABLE = "notification_tracker_enable"
    }
}

