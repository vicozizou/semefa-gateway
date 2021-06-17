package com.saludaunclic.semefa.gateway.component

import com.ibm.mq.MQGetMessageOptions
import com.ibm.mq.MQMessage
import com.ibm.mq.MQQueue
import com.ibm.mq.MQQueueManager
import com.ibm.mq.constants.CMQC
import com.saludaunclic.semefa.gateway.config.MqClientConfig
import com.saludaunclic.semefa.gateway.model.GatewayConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Component
import java.util.Hashtable

@Component
class MqClient(val mqClientConfig: MqClientConfig) {
    companion object {
        const val NUMBER_OF_GET_TRIES = 2
    }

    private val connectionProps = Hashtable<String, Any>()
        .apply {
            with(mqClientConfig) {
                put(CMQC.CHANNEL_PROPERTY, channel)
                put(CMQC.PORT_PROPERTY, port)
                put(CMQC.HOST_NAME_PROPERTY, hostname)
                put(CMQC.APPNAME_PROPERTY, "Aplicacion Afiliacion Online JAVA, SEMEFA-SUSALUD V1.0")
                put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT)
                /*logger.info("""
                    Loaded MQ properties:
                    =====================
                    queueManagerName: $queueManager
                    queueNameIn: $queueIn
                    queueNameOut: $queueOut
                    hostname: $hostname 
                    port: $port
                    channel: $channel
                """.trimIndent())*/
            }
        }
    private val messageOptions = MQGetMessageOptions()
        .apply {
            matchOptions = CMQC.MQMO_MATCH_MSG_ID
            options = CMQC.MQGMO_WAIT
            waitInterval = 1000
        }
    private val logger: Logger = LoggerFactory.getLogger(MqClient::class.java)

    fun sendMessageSync(message: String): Map<String, String> {
        val response = mutableMapOf<String, String>()
        var queueManager: MQQueueManager? = null
        var queueIn: MQQueue? = null
        var queueOut: MQQueue? = null
        var putMessage: MQMessage? = null
        var getMessage: MQMessage? = null

        try {
            with(mqClientConfig) {
                logger.info("Connecting to queue manager: ${this.queueManager}")
                queueManager = MQQueueManager(this.queueManager, connectionProps)
                logger.info("Connected to: ${this.queueManager}")
            }

            queueIn = accessQueue(queueManager, true)
            putMessage = createPutMessage(message)
            queueIn?.put(putMessage)

            queueOut = accessQueue(queueManager, false)
            getMessage = createGetMessage(putMessage)
            fetchResponse(queueOut, getMessage)

            processResponse(response, getMessage)
        } finally {
            closeResources(putMessage, getMessage, queueIn, queueOut, queueManager)
        }

        return response
    }

    private fun accessQueue(queueManager: MQQueueManager?, isIn: Boolean): MQQueue? =
        with(mqClientConfig) {
            val name = if (isIn) queueIn else queueOut
            logger.info("Accessing queue $name.. ")
            val queue = queueManager?.accessQueue(
                name,
                (if (isIn) CMQC.MQOO_OUTPUT else CMQC.MQOO_INPUT_AS_Q_DEF) or CMQC.MQOO_FAIL_IF_QUIESCING)
            logger.info("Queue $name accessed")
            queue
        }

    private fun fetchResponse(queueOut: MQQueue?, message: MQMessage?) {
        for(i in 1..NUMBER_OF_GET_TRIES) {
            try {
                queueOut?.get(message, messageOptions)
                break
            } catch (e: Exception) {
                logger.info("Attempt #$i to fetch response message has failed")
                if (i < NUMBER_OF_GET_TRIES) {
                    logger.info("Giving another try")
                }
            }
        }
    }

    private fun processResponse(response: MutableMap<String, String>, message: MQMessage) {
        if (message.dataLength == 0) {
            logger.warn("Get message does not contain any data")
            return
        }

        logger.info("Ready message fetch")
        val xmlDataFrame = with(message) { readStringOfByteLength(dataLength) }
        logger.info("Message got: $xmlDataFrame")
        val messageId = String(Hex.encode(message.messageId))
        logger.info("Msg Id: $messageId")

        response[GatewayConstants.MESSAGE_ID_KEY] = messageId
        response[GatewayConstants.MESSAGE_KEY] = xmlDataFrame
    }

    private fun closeResources(putMessage: MQMessage?,
                               getMessage: MQMessage?,
                               queueIn: MQQueue?,
                               queueOut: MQQueue?,
                               queueManager: MQQueueManager?) {
        // Clear messages
        logger.info("Clear message put")
        putMessage?.clearMessage()
        logger.info("Clear message get")
        getMessage?.clearMessage()
        // Close the queues
        logger.info("Closing the queueIn")
        queueIn?.close()
        logger.info("Closing the queueOut")
        queueOut?.close()
        // Disconnect from the QueueManager
        logger.info("Disconnecting from the Queue Manager")
        queueManager?.disconnect()
    }

    private fun createPutMessage(message: String): MQMessage = MQMessage()
        .apply {
            logger.info("Creating PUT message")
            characterSet = 819
            encoding = 273
            format = CMQC.MQFMT_STRING
            writeString(message)
            logger.info("Message: [ $message ]")
        }

    private fun createGetMessage(message: MQMessage): MQMessage = MQMessage()
        .apply {
            logger.info("Creating GET message")
            characterSet = 819
            encoding = 273
            messageId = message.messageId
            logger.info("Message: [ ${message.messageId} ]")
        }
}
