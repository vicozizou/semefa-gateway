package com.saludaunclic.semefa.gateway.model

class DataFrame997(
    val feTransaccion: String? = null,
    val hoTransaccion: String? = null,
    val idCorrelativo: String? = null,
    val idReceptor: String? = null,
    val idRemitente: String? = null,
    val idTransaccion: String? = null,
    val noTransaccion: String? = null,
    val nuControl: String? = null,
    val nuControlST: String? = null,
    val excepciones: List<List<String>>? = emptyList()
) {
    override fun toString(): String =
        "DataFrame997(" +
            "feTransaccion=$feTransaccion, " +
            "hoTransaccion='$hoTransaccion', " +
            "idCorrelativo='$idCorrelativo', " +
            "idReceptor='$idReceptor', " +
            "idRemitente='$idRemitente', " +
            "idTransaccion='$idTransaccion', " +
            "noTransaccion='$noTransaccion', " +
            "nuControl='$nuControl', " +
            "nuControlST=$nuControlST" +
        ")"
}