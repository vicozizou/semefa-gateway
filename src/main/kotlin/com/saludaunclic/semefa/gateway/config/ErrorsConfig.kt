package com.saludaunclic.semefa.gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "errors")
class ErrorsConfig {
    lateinit var fieldErrorFile: String
    lateinit var descriptionErrorFile: String
}
