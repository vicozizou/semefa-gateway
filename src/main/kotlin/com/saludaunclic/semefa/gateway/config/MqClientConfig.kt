package com.saludaunclic.semefa.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MqClientConfig(@Value("\${mq.queue-manager}") val queueManager: String,
                     @Value("\${mq.queue-in}") val queueIn: String,
                     @Value("\${mq.queue-out}") val queueOut: String,
                     @Value("\${mq.channel}") val channel: String,
                     @Value("\${mq.hostname}") val hostname: String,
                     @Value("\${mq.port}") val port: Int)