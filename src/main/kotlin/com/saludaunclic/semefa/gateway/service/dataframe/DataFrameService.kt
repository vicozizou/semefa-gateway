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
import java.util.*

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

    private fun parseRequest(in271RegafiUpdate: In271RegafiUpdate) =
        with(in271RegafiUpdate) {
            """
                <?xml version="1.0" encoding="UTF-8"?>
                <sus:BusqDatosAseguradoRequest xmlns:sus="http://www.susalud.gob.pe/acreditacion/BusqDatosAseguradoRequest.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.susalud.gob.pe/acreditacion/BusqDatosAseguradoRequest.xsd ../MsgSetProjBusqDatosAseguradoRequest/importFiles/pe/gob/susalud/www/acreditacion/busqdatosaseguradorequestxsd/BusqDatosAseguradoRequest.xsd ">
                  <txNombre>$noTransaccion</txNombre>
                  <coRemitente>$idRemitente</coRemitente>
                  <txPeticion>${regafiUpdate271Service.beanToX12N(this)}</txPeticion>
                </sus:BusqDatosAseguradoRequest>
                  """
                .trimIndent()
                .replace(System.lineSeparator(), "")
        }

    private fun processRequest(in271RegafiUpdate: In271RegafiUpdate): String =
        with(in271RegafiUpdate) {
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, bean: \n${objectMapper.writeValueAsString(this)}")
            }

            parseRequest(this)
                .also {
                    if (logger.isDebugEnabled) {
                        logger.debug("From bean to X12, X12: \n$it")
                    }
                }
        }

    private fun putAndGetMessage(dataFrame: String): Map<String, String> =
        with(dataFrame) {
            logger.info("=== Start MQ Connection ===")
            if (logger.isDebugEnabled) {
                logger.debug("Sending X12 message with this data: $this")
            }

            mqClient
                .sendMessageSync(this)
                .also { logger.info("=== End MQ Connection ===") }
        }

    private fun processResponse(response: Map<String, String>): In997RegafiUpdate =
        with(response) {
            val x12: String = extractX12(this[GatewayConstants.MESSAGE_KEY] ?: "", GatewayConstants.TAG_997)
            if (logger.isDebugEnabled) {
                logger.debug("From X12 to bean, X12: \n$x12")
            }

            regafiUpdate997Service
                .x12NToBean(x12)
                .apply { isFlag = true }
                .also {
                    if (logger.isDebugEnabled) {
                        logger.debug("From X12 to bean, bean: \n${objectMapper.writeValueAsString(it)}")
                    }
                }
        }

    private fun extractX12(xmlText: String, tag: String): String {
        if (logger.isDebugEnabled) {
            logger.debug("Extracting X12 from $xmlText with tag $tag")
        }

        val split = xmlText.split(tag)
        val second = split[if (split.size > 1) 1 else 0]
        return second
            .substring(1, second.length - 2)
            .also { logger.debug("X12 extracted: $this") }
    }
}