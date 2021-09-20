package com.saludaunclic.semefa.gateway.service.date

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.Date

interface DateService {
    fun now(): LocalDateTime
    fun toDate(dateTime: LocalDateTime): Date
    fun toTimestamp(dateTime: LocalDateTime): Timestamp
    fun nowTimestamp(): Timestamp
    fun formatDate(dateTime: LocalDateTime): String
    fun formatTime(dateTime: LocalDateTime): String
}
