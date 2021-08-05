package com.saludaunclic.semefa.gateway.service.auth

import com.saludaunclic.semefa.gateway.model.User
import java.util.*

interface UserAuthenticationService {
    fun login(username: String, password: String): Optional<String>

    fun findByToken(token: String): Optional<User>

    fun logout(user: User)
}