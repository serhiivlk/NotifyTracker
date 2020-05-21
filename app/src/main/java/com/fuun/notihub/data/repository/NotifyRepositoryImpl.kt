package com.fuun.notihub.data.repository

import com.fuun.notihub.data.db.dao.NotifyDao
import com.fuun.notihub.data.db.model.NotifyCachedRaw
import com.fuun.notihub.data.mapper.NotifyCachedRawMapper
import com.fuun.notihub.domain.entities.Notify
import com.fuun.notihub.domain.repository.NotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

class NotifyRepositoryImpl constructor(
    private val notifyDao: NotifyDao,
    private val notifyMapper: NotifyCachedRawMapper
) : NotifyRepository {
    override fun observeNotification(): Flow<List<Notify>> {
        return notifyDao.observeAll()
            .map { list -> list.map(notifyMapper::map) }
    }

    override suspend fun storeNotify(key: String, appPackage: String, text: String, date: Long) {
        notifyDao.insert(
            NotifyCachedRaw(
                key = key,
                appPackage = appPackage,
                text = text,
                created = date.toOffsetDateTime()
            )
        )
    }

    private fun Long.toOffsetDateTime() =
        Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toOffsetDateTime()
}
