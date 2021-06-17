package com.saludaunclic.semefa.gateway.component

import com.saludaunclic.semefa.gateway.model.GatewayConstants
import com.saludaunclic.semefa.gateway.model.DataFrame271
import com.saludaunclic.semefa.gateway.model.DataFrame997
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate997Service

@Service
class DataFrameProcessor(
    val regafiUpdate271Service: RegafiUpdate271Service,
    val regafiUpdate997Service: RegafiUpdate997Service,
    val dataFrames: DataFrames,
    val mqClient: MqClient,
) {
    private val logger: Logger = LoggerFactory.getLogger(DataFrameProcessor::class.java)

    fun process271DataFrame(x12Text: String): DataFrame271 =
        dataFrames.fromIn271(
            regafiUpdate271Service.x12NToBean(
                extractX12(
                    x12Text,
                    GatewayConstants.TAG_271)))

    fun processDataFrame997(dataFrame: String): DataFrame997 {
        logger.info("=== Start MQ Connection ===")
        val response = mqClient.sendMessageSync(dataFrame)
        logger.info("=== End MQ Connection ===")

        return dataFrames.fromIn997(
            regafiUpdate997Service.x12NToBean(
                extractX12(
                    response[GatewayConstants.MESSAGE_KEY] ?: "",
                    GatewayConstants.TAG_997
                )).apply { isFlag = true },
            response[GatewayConstants.MESSAGE_KEY] ?: "",
            response[GatewayConstants.MESSAGE_ID_KEY] ?: ""
        )
    }

    private fun extractX12(xmlText: String, tag: String): String {
        logger.info("Extracting x12 from $xmlText with tag $tag")
        val x12Split: List<String> = xmlText.split(tag)
        val second = x12Split[1]
        val x12 = second.substring(1, second.length - 2)
        logger.debug("X12 extracted: $x12")
        return x12
    }
}