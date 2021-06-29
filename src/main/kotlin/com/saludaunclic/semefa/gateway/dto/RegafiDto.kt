package com.saludaunclic.semefa.gateway.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class RegafiDto(
    @JsonProperty("noTransaccion") var noTransaccion: String? = null,
    @JsonProperty("idRemitente") var idRemitente: String? = null,
    @JsonProperty("idReceptor") var idReceptor: String? = null,
    @JsonProperty("feTransaccion") var feTransaccion: String? = null,
    @JsonProperty("hoTransaccion") var hoTransaccion: String? = null,
    @JsonProperty("idCorrelativo") var idCorrelativo : String? = null,
    @JsonProperty("idTransaccion") var idTransaccion: String? = null,
    @JsonProperty("tiFinalidad") var tiFinalidad   : String? = null,
    @JsonProperty("caRemitente") var caRemitente: String? = null,
    @JsonProperty("tiOperacion") var tiOperacion: String? = null,
    @JsonProperty("afiliado") var afiliado: AfiliadoDto? = null,
    @JsonProperty("afiliacion") var afiliacionDto: AfiliacionDto? = null
): Serializable

