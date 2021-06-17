package com.saludaunclic.semefa.gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "mq")
class MqClientConfig {
    companion object {
        const val DEFAULT_PORT: Int = 1414
    }

    lateinit var hostname: String
    var port: Int = DEFAULT_PORT
    lateinit var channel: String
    lateinit var queueManager: String
    lateinit var queueIn: String
    lateinit var queueOut: String
}