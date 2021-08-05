package com.saludaunclic.semefa.gateway.service.date

import java.time.LocalDateTime
import java.util.*

class DefaultDateService(val timeZone: TimeZone): DateService {
    init {
        System.setProperty("user.timezone", this.timeZone.id);
        TimeZone.setDefault(this.timeZone);
    }

    override fun now(): LocalDateTime = LocalDateTime.now()

    override fun toDate(dateTime: LocalDateTime): Date = Date.from(dateTime.atZone(timeZone.toZoneId()).toInstant())


}
