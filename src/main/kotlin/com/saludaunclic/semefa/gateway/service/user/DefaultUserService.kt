package com.saludaunclic.semefa.gateway.service.user

import com.saludaunclic.semefa.gateway.model.User
import com.saludaunclic.semefa.gateway.repository.UserRepository
import com.saludaunclic.semefa.gateway.throwing.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.jvm.Throws

@Service
class DefaultUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
): UserService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(ServiceException::class)
    override fun save(user: User): User = _save(normalize(user))

    override fun find(id: Int): Optional<User> = userRepository.findById(id)

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun userCount(): Long = userRepository.count()

    override fun passwordMatches(provided: String, current: String): Boolean =
        passwordEncoder.matches(provided, current)

    private fun _save(user: User): User = if (user.id != null) update(user) else create(user)

    @Throws(ServiceException::class)
    fun create(user: User): User {
        logger.info("Creating user: $user")

        val existent = userRepository.findByUsername(user.username)
        if (existent.isPresent) {
            throw ServiceException("Usuario ${user.username} con ese nombre ya existe", HttpStatus.CONFLICT)
        }

        return createOrUpdate(user)
    }

    @Throws(ServiceException::class)
    fun update(user: User): User {
        logger.info("Creating user: $user")

        val existent = userRepository.findByUsername(user.username)
        if (!existent.isPresent) {
            throw ServiceException("Usuario ${user.username} no existe", HttpStatus.NOT_FOUND)
        }

        return createOrUpdate(user)
    }

    private fun normalize(user: User) = user.apply { username = username.lowercase() }

    private fun createOrUpdate(user: User): User =
        with(user) {
            val saved = userRepository
            .save(user.apply {
                if (this.encrypted) {
                    password = passwordEncoder.encode(password)
                }
            })
            logger.info("User saved: $saved")
            saved
        }

}
