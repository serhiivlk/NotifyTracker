package com.fuun.notihub.data.db.converter

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun to(value: String?): OffsetDateTime? = value?.let {
        formatter.parse(it, OffsetDateTime::from)
    }

    @TypeConverter
    fun from(dateTime: OffsetDateTime?): String? = dateTime?.format(formatter)
}
