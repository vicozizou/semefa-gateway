package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}
