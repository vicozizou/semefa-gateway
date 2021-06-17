package com.saludaunclic.semefa.gateway.model

import com.saludaunclic.semefa.gateway.x12.data.fromIn271
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate

class DataFrame271(
    var tiOperacion: String? = null,
    val caRemitente: String? = null,
    val feTransaccion: String? = null,
    val hoTransaccion: String? = null,
    val idCorrelativo: String? = null,
    val idReceptor: String? = null,
    val idRemitente: String? = null,
    val idTransaccion: String? = null,
    val noTransaccion: String? = null,
    val tiFinalidad: String? = null,
    val data: List<List<String>>? = emptyList(),
    val dataExt: List<List<String>>? = emptyList()
) {
    constructor(in271: In271RegafiUpdate): this() {
        fromIn271(in271)
    }

    override fun toString(): String =
        "DataFrame271(" +
            "tiOperacion=$tiOperacion, " +
            "caRemitente=$caRemitente, " +
            "feTransaccion=$feTransaccion, " +
            "hoTransaccion=$hoTransaccion, " +
            "idCorrelativo=$idCorrelativo, " +
            "idReceptor=$idReceptor, " +
            "idRemitente=$idRemitente, " +
            "idTransaccion=$idTransaccion, " +
            "noTransaccion=$noTransaccion, " +
            "tiFinalidad=$tiFinalidad" +
        ")"
}