package com.fuun.notihub.domain.entities

import java.time.LocalDateTime
import java.time.OffsetDateTime

data class Notify(
    val key: String,
    val appPackage: String,
    val text: String,
    val date: LocalDateTime
)
