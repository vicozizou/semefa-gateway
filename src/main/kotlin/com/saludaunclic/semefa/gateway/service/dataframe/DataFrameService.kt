package com.saludaunclic.semefa.gateway.service.dataframe

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.gateway.GatewayConstants
import com.saludaunclic.semefa.gateway.component.MqClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate997Service

@Service
class DataFrameService(
    private val regafiUpdate271Service: RegafiUpdate271Service,
    private val regafiUpdate997Service: RegafiUpdate997Service,
    private val mqClient: MqClient,
    private val objectMapper: ObjectMapper
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun process271DataFrame(in271RegafiUpdate: In271RegafiUpdate): In997RegafiUpdate =
        processResponse(putAndGetMessage(processRequest(in271RegafiUpdate)))

    private fun putAndGetMessage(dataFrame: String): Map<String, String> =
        with(dataFrame) {
            logger.info("=== Start MQ Connection ===")
            if (logger.isDebugEnabled) {
                logger.debug("Sending X12 message with this data: $this")
            }

            val response: Map<String, String> = mqClient.sendMessageSync(this)
            logger.info("=== End MQ Connection ===")
            response
        }

    private fun processRequest(in271RegafiUpdate: In271RegafiUpdate): String =
        with(in271RegafiUpdate) {
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, bean: \n${objectMapper.writeValueAsString(this)}")
            }
            val x12: String = extractX12(regafiUpdate271Service.beanToX12N(this), GatewayConstants.TAG_271)
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, X12: \n${x12}")
            }
            x12
        }

    private fun processResponse(response: Map<String, String>): In997RegafiUpdate =
        with(response) {
            val x12: String = extractX12(this[GatewayConstants.MESSAGE_KEY] ?: "", GatewayConstants.TAG_997)
            if (logger.isDebugEnabled) {
                logger.debug("From X12 to bean, X12: \n$x12")
            }

            val in997RegafiUpdate: In997RegafiUpdate = regafiUpdate997Service
                .x12NToBean(x12)
                .apply { isFlag = true }

            if (logger.isDebugEnabled) {
                logger.debug("From X12 to bean, bean: \n${objectMapper.writeValueAsString(in997RegafiUpdate)}")
            }
            in997RegafiUpdate
        }

    private fun extractX12(xmlText: String, tag: String): String {
        logger.info("Extracting x12 from $xmlText with tag $tag")
        val x12Split: List<String> = xmlText.split(tag)
        val second = x12Split[1]
        val result = second.substring(1, second.length - 2)
        logger.debug("X12 extracted: $this")
        return result
    }
}