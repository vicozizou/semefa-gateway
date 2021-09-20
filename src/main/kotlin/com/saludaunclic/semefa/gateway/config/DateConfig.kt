package com.saludaunclic.semefa.gateway.config

import com.saludaunclic.semefa.gateway.service.date.DateService
import com.saludaunclic.semefa.gateway.service.date.DefaultDateService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
@ConfigurationProperties(prefix = "date")
class DateConfig(@Value("\${date.time-zone}") val timeZone: String,) {
    fun defaultTimeZone(): TimeZone = TimeZone.getTimeZone(timeZone)

    @Bean fun dateService(): DateService = DefaultDateService(defaultTimeZone())
}
