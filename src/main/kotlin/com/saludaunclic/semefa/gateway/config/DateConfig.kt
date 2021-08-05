package com.saludaunclic.semefa.gateway.config

import com.saludaunclic.semefa.gateway.service.date.DateService
import com.saludaunclic.semefa.gateway.service.date.DefaultDateService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties(prefix = "date")
class DateConfig {
    @Bean fun defaultTimeZone(): TimeZone = TimeZone.getTimeZone("UTC")

    @Bean fun dateService(): DateService = DefaultDateService(defaultTimeZone())
}
