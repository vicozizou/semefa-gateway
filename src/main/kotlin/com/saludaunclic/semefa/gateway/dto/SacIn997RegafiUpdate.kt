package com.saludaunclic.semefa.gateway.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class SacIn997RegafiUpdate(
    @JsonProperty("idMensaje") var idMensaje: String? = null,
    @JsonProperty("intentos") var intentos: Int? = null,
    @JsonProperty("mensajeError") var mensajeError: String? = null,
    @JsonProperty("in271RegafiUpdateExcepcion")
    var in271RegafiUpdateExcepcion: List<SacIn997RegafiUpdateExcepcion> = listOf()
): In997RegafiUpdate(), Serializable
