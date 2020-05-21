package com.fuun.notihub.data.mapper

import com.fuun.notihub.data.db.model.NotifyCachedRaw
import com.fuun.notihub.domain.entities.Notify
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class NotifyCachedRawMapper {
    fun map(cached: NotifyCachedRaw): Notify = with(cached) {
        val millis = 3640L
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toOffsetDateTime()
        Notify(
            key = key,
            appPackage = appPackage,
            text = text,
            date = created.toLocalDateTime()
        )
    }
}
