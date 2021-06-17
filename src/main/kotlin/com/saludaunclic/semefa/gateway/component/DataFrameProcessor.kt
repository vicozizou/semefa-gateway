package com.saludaunclic.semefa.gateway.component

import com.saludaunclic.semefa.gateway.model.Constants
import com.saludaunclic.semefa.gateway.model.DataFrame271
import com.saludaunclic.semefa.gateway.model.DataFrame997
import com.saludaunclic.semefa.gateway.x12.data.extractX12
import com.saludaunclic.semefa.gateway.x12.data.fromIn271
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate997Service

@Service
class DataFrameProcessor(
    val regafiUpdate271Service: RegafiUpdate271Service,
    val regafiUpdate997Service: RegafiUpdate997Service
) {
    private val logger: Logger = LoggerFactory.getLogger(DataFrameProcessor::class.java)

    fun process271DataFrame(x12Text: String): DataFrame271 {
        logger.debug("Processing x12 text: $x12Text")
        return fromIn271(regafiUpdate271Service.x12NToBean(extractX12(x12Text, Constants.TAG_271)))
    }

    fun processDataFrame997(dataFrame: String): DataFrame997 {
        return DataFrame997()
    }
}