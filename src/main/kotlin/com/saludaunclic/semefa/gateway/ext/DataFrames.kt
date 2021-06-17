package com.saludaunclic.semefa.gateway.x12.data

import com.saludaunclic.semefa.gateway.model.DataFrame271
import com.saludaunclic.semefa.gateway.component.DataFrameProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdateAfiliacion
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdateAfiliado

val logger: Logger = LoggerFactory.getLogger(DataFrameProcessor::class.java)

fun extractX12(xmlText: String, tag: String): String {
    logger.info("Extracting x12 from $xmlText with tag $tag")
    val x12Split: List<String> = xmlText.split(tag)
    val second = x12Split.get(1)
    val x12 = second.substring(1, second.length - 2)
    logger.debug("X12 extracted: $x12")
    return x12
}

fun fromIn271(in271: In271RegafiUpdate): DataFrame271 =
    with(in271) {
        DataFrame271(
            tiOperacion,
            caRemitente,
            feTransaccion,
            hoTransaccion,
            idCorrelativo,
            idReceptor,
            idRemitente,
            idTransaccion,
            noTransaccion,
            tiFinalidad,
            fromAfiliados(detallesAfiliados),
            fromAfiliaciones(detallesAfiliaciones)
        )
    }

private fun fromAfiliados(afiliados: List<In271RegafiUpdateAfiliado>): List<List<String>> =
    afiliados.map { fromAfiliado(it) }

private fun fromAfiliaciones(afiliaciones: List<In271RegafiUpdateAfiliacion>): List<List<String>> =
    afiliaciones.map { fromAfiliacion(it) }

private fun fromAfiliado(afiliado: In271RegafiUpdateAfiliado): List<String> =
    with(afiliado) {
        listOf<String>(
            resolverNombreAfiliado(this),
            apPaternoAfiliado,
            apMaternoAfiliado,
            tiDocumentoAfil,
            nuDocumentoAfil,
            estadoAfiliado,
            tiDocumentoAct,
            nuDocumentoAct,
            apCasadaAfiliado,
            coNacionalidad,
            ubigeo,
            feNacimiento,
            genero,
            coPaisDoc,
            fefallecimiento,
            coPaisEmisorDocAct,
            feActPersonaxIafas,
            tiDocTutor,
            nuDocTutor,
            tiVinculoTutor,
            feValidacionReniec,
            coPaisEmisorDocTutor,
        )
    }

private fun fromAfiliacion(afiliacion: In271RegafiUpdateAfiliacion): List<String> =
    with(afiliacion) {
        listOf<String>(
            tiDocTitular,
            nuDocTitular,
            feNacimientoTitular,
            coPaisEmisorDocTitular,
            tiContratante,
            resolverNombreContratante(this),
            apPaternoContratante,
            apMaternoContratante,
            tiDocContratante,
            nuDocContratante,
            apCasadaContratante,
            feNacimientoContratante,
            coPaisEmisorDocContratante,
            coAfiliacion,
            coContrato,
            coUnicoMultisectorial,
            tiregimen,
            esAfiliacion,
            coCausalBaja,
            tiPlanSalud,
            noProductoSalud,
            coProducto,
            parentesco,
            coRenipress,
            pkAfiliado,
            feActEstado,
            feIniAfiliacion,
            feFinAfiliacion,
            feIniCobertura,
            feFinCobertura,
            feActOperacion,
            //tiActOperacion,
            coTiCobertura,
            //idAfiliacionNombre
        )
    }

private fun appendIfNotBlank(builder: StringBuilder, value: String?, separator: String = "") {
    if (!value.isNullOrBlank()) {
        builder.append(separator).append(value)
    }
}

private fun resolverSeparator(value: String, separator: String = " "): String = if ("1" == value) separator else ""

private fun resolverNombreAfiliado(afiliado: In271RegafiUpdateAfiliado): String {
    with(afiliado) {
        return buildString {
            appendIfNotBlank(this, noAfiliado1)
            appendIfNotBlank(this, noAfiliado2, resolverSeparator(idAfiliadoNombre))
        }
    }
}

private fun resolverNombreContratante(afiliacion: In271RegafiUpdateAfiliacion): String {
    with(afiliacion) {
        return buildString {
            appendIfNotBlank(this, noContratante1)

            val isAfiliacionName = idAfiliacionNombre.trim()
            for (i in 0 until 3) {
                val c = isAfiliacionName[i] + ""
                when(i) {
                    0 -> appendIfNotBlank(this, noContratante2, resolverSeparator(c))
                    1 -> appendIfNotBlank(this, noContratante3, resolverSeparator(c))
                    2 -> appendIfNotBlank(this, noContratante4, resolverSeparator(c))
                }
            }
        }
    }
}
