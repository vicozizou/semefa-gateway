package com.saludaunclic.semefa.gateway


class GatewayConstants {
    companion object {
        const val TAG_271 = "txPeticion"
        const val TAG_997 = "txRespuesta"
        const val SEPARATOR = "\\|"
        const val MESSAGE_ID_KEY = "MsgId"
        const val MESSAGE_KEY = "Msg"
        const val SUPER_ROLE = "SUPER"
    }
}

object Messages {
    const val USERNAME_SIZE_VALIDATION: String = "Usuario debe tener al menos 8 y como máximo 16 caracteres"
    const val PASSWORD_SIZE_VALIDATION: String = "Contraseña debe tener al menos 12 y como máximo 32 caracteres"
}
