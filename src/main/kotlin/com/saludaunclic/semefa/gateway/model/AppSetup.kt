package com.saludaunclic.semefa.gateway.model

import com.saludaunclic.semefa.gateway.Messages
import javax.validation.constraints.Size

class AppSetup(
    @Size(min = 8, max = 16, message = Messages.USERNAME_SIZE_VALIDATION)
    val username: String,
    @Size(min = 12, max = 32, message = Messages.PASSWORD_SIZE_VALIDATION)
    val password: String
) {
    override fun toString(): String = "AppSetup(username='$username')"
}
