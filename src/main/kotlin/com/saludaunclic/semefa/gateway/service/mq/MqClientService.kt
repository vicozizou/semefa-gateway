package com.saludaunclic.semefa.gateway.service.mq

import com.ibm.mq.MQException
import com.ibm.mq.MQGetMessageOptions
import com.ibm.mq.MQMessage
import com.ibm.mq.MQQueue
import com.ibm.mq.constants.CMQC
import com.saludaunclic.semefa.gateway.config.MqClientConfig
import com.saludaunclic.semefa.gateway.throwing.MqMaxAttemptReachedException
import com.saludaunclic.semefa.gateway.throwing.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import java.util.Hashtable
import java.util.concurrent.TimeUnit

@Service
class MqClientService(val mqClientConfig: MqClientConfig) {
    companion object {
        const val MESSAGE_ID_KEY = "MsgId"
        const val MESSAGE_KEY = "Msg"
        const val NUMBER_OF_GET_TRIES = 1
        const val CHARACTER_SET = 819
        const val ENCODING = 273
        const val WAIT_INTERVAL = 3000
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val connectionProps = Hashtable<String, Any>()
        .apply {
            with(mqClientConfig) {
                put(CMQC.CHANNEL_PROPERTY, channel)
                put(CMQC.PORT_PROPERTY, port)
                put(CMQC.HOST_NAME_PROPERTY, hostname)
                put(CMQC.APPNAME_PROPERTY, "Aplicacion Afiliacion Online JAVA, SEMEFA-SUSALUD V1.0")
                put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT)
                logger.info("""

                    Loaded MQ properties:
                    =====================
                    queueManagerName: $queueManager
                    queueNameIn: $queueIn
                    queueNameOut: $queueOut
                    hostname: $hostname 
                    port: $port
                    channel: $channel
                """.trimIndent())
            }
        }

    fun sendMessageSync(message: String): Map<String, String> {
        logger.info("Connecting to queue manager: ${mqClientConfig.queueManager} to send update")
        val wrapper = QueueManagerWrapper(mqClientConfig)

        try {
            wrapper.wrap(connectionProps)
            val putMessage: MQMessage = createPutMessage(message)
            wrapper.queueIn.put(putMessage)
            return getMessageResponse(wrapper.queueOut, createGetMessage(putMessage.messageId))
        } catch (ex: MQException) {
            throw ServiceException("Error conectándose a MQ", ex, HttpStatus.SERVICE_UNAVAILABLE)
        } finally {
            wrapper.closeResources()
        }
    }

    fun fetchMessage(messageId: String): Map<String, String> {
        logger.info("Connecting to queue manager: ${mqClientConfig.queueManager} to recover message $messageId")
        val wrapper = QueueManagerWrapper(mqClientConfig)

        try {
            wrapper.wrap(connectionProps)
            return getMessageResponse(wrapper.queueOut, createPutMessage(messageId))
        } catch (ex: MQException) {
            throw ServiceException("Error conectándose a MQ", ex, HttpStatus.SERVICE_UNAVAILABLE)
        } finally {
            wrapper.closeResources()
        }
    }

    private fun createMessageOptions(): MQGetMessageOptions = MQGetMessageOptions()
        .apply {
            matchOptions = CMQC.MQMO_MATCH_MSG_ID
            options = CMQC.MQGMO_WAIT
            waitInterval = WAIT_INTERVAL
        }

    private fun getMessageResponse(queueOut: MQQueue, message: MQMessage): MutableMap<String, String> {
        fetchResponse(queueOut, message)
        return processResponse(message)
    }

    private fun fetchResponse(queueOut: MQQueue, message: MQMessage) {
        for(i in 1..NUMBER_OF_GET_TRIES) {
            try {
                queueOut.get(message, createMessageOptions())
                return
            } catch (e: Exception) {
                logger.info("Attempt #$i to fetch response message has failed")
                if (i < NUMBER_OF_GET_TRIES) {
                    logger.info("Giving another try")
                    TimeUnit.SECONDS.sleep(1)
                }
            }
        }

        throw MqMaxAttemptReachedException(
            Hex.encode(message.messageId).toString(),
            "Número máximo de intentos para conectarse a MQ se alcanzó, abortando")
    }

    private fun processResponse(message: MQMessage): MutableMap<String, String> {
        val response = mutableMapOf<String, String>()

        if (message.dataLength == 0) {
            logger.warn("Get message does not contain any data")
            return response
        }

        logger.info("Ready message fetch")
        val xmlDataFrame: String = message.readStringOfByteLength(message.dataLength)
        logger.debug("Message got: $xmlDataFrame")

        val messageId = String(Hex.encode(message.messageId))
        logger.info("Msg Id: [ $messageId ]")

        response[MESSAGE_ID_KEY] = messageId
        response[MESSAGE_KEY] = xmlDataFrame

        return response
    }

    private fun createPutMessage(message: String): MQMessage =
        MQMessage()
            .apply {
                logger.info("Creating PUT message")
                characterSet = CHARACTER_SET
                encoding = ENCODING
                format = CMQC.MQFMT_STRING
                writeString(message)
            }

    private fun createGetMessage(messageId: ByteArray): MQMessage =
        MQMessage()
            .apply {
                logger.info("Creating GET message")
                characterSet = CHARACTER_SET
                encoding = ENCODING
                this.messageId = messageId
                logger.debug("Message: [ ${this.messageId} ]")
            }
}
