package com.saludaunclic.semefa.gateway.service.user

import com.saludaunclic.semefa.gateway.model.User
import com.saludaunclic.semefa.gateway.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class DefaultUserService(val userRepository: UserRepository,
                         val passwordEncoder: BCryptPasswordEncoder): UserService {
    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun find(id: String): Optional<User> = userRepository.findById(id)

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun passwordMatches(provided: String, current: String): Boolean =
        passwordEncoder.matches(provided, current)
}
