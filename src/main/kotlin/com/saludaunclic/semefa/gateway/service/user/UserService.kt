package com.saludaunclic.semefa.gateway.service.user

import com.saludaunclic.semefa.gateway.model.User
import java.util.Optional

interface UserService {
    fun save(user: User)
    fun find(id: String): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun passwordMatches(provided: String, current: String): Boolean
}
