package com.fuun.notihub.domain.repository

import com.fuun.notihub.domain.entities.Notify
import kotlinx.coroutines.flow.Flow

interface NotifyRepository {
    fun observeNotification(): Flow<List<Notify>>
    suspend fun storeNotify(key: String, appPackage: String, text: String, date: Long)
}
