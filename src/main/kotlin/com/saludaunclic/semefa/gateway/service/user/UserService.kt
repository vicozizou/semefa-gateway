package com.saludaunclic.semefa.gateway.service.user

import com.saludaunclic.semefa.gateway.model.User
import java.util.*

interface UserService {
    fun save(user: User): User
    fun find(id: Int): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun userCount(): Long
    fun passwordMatches(provided: String, current: String): Boolean
}
