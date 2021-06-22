package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: CrudRepository<User, String> {
    @Query("SELECT * FROM AppUser WHERE username = :username")
    fun findByUsername(username: String): Optional<User>
}
