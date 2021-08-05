package com.saludaunclic.semefa.gateway.service.date

import java.time.LocalDateTime
import java.util.*

interface DateService {
    fun now(): LocalDateTime
    fun toDate(dateTime: LocalDateTime): Date
}
