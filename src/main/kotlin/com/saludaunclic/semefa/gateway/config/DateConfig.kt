package com.saludaunclic.semefa.gateway.config

import com.saludaunclic.semefa.gateway.date.DateService
import com.saludaunclic.semefa.gateway.date.DefaultDateService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
@ConfigurationProperties(prefix = "date")
class DateConfig {
    @Bean fun defaultTimeZone(): TimeZone = TimeZone.getTimeZone("UTC")

    @Bean fun dateService(): DateService = DefaultDateService(defaultTimeZone())
}