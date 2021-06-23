package com.saludaunclic.semefa.gateway.service.user

import com.saludaunclic.semefa.gateway.model.Role
import com.saludaunclic.semefa.gateway.model.User
import com.saludaunclic.semefa.gateway.repository.RoleRepository
import com.saludaunclic.semefa.gateway.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class DefaultUserService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: BCryptPasswordEncoder
): UserService {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun save(user: User): User {
        logger.info("Adding user: $user")

        val existing: Set<String> = roleRepository.findAll().map { it.id }.toSet()
        val notMapped = mutableSetOf<Role>()
        user.roles
            .forEach {
                if (!existing.contains(it.roleId)) {
                    logger.info("Role ${it} does not exits, adding it")
                    val role = Role(it.roleId)
                    roleRepository.save(role)
                    notMapped.add(role)
                }
            }

        val saved: User = userRepository
            .save(user.apply {
                if (this.encrypted) {
                    password = passwordEncoder.encode(password)
                }
            })
        logger.info("User saved: $saved")

        return saved
    }

    override fun find(id: String): Optional<User> = userRepository.findById(id)

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun userCount(): Long = userRepository.count()

    override fun passwordMatches(provided: String, current: String): Boolean =
        passwordEncoder.matches(provided, current)
}
