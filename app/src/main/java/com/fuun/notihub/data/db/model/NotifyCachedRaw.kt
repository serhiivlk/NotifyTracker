package com.fuun.notihub.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "notifications")
class NotifyCachedRaw(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "app_package")
    val appPackage: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "created_date")
    val created: OffsetDateTime
)
