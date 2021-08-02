package com.saludaunclic.semefa.gateway.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class QueueConfig(
    @Value("\${queue.regafi-queue}") val regafiQueueName: String,
    @Value("\${queue.regafi-exchange}") val regafiExchangeName: String
) {
    @Bean
    fun queue(): Queue = Queue(regafiQueueName, false)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(regafiExchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with("regafi.update")
    }

    /*@Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver, "receiveMessage").
    }*/

    /*@Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter!!)
        return container
    }*/
}