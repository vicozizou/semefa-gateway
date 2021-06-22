package com.saludaunclic.semefa.gateway.service.token

import com.saludaunclic.semefa.gateway.model.User
import com.saludaunclic.semefa.gateway.service.auth.UserAuthenticationService
import com.saludaunclic.semefa.gateway.service.user.UserService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TokenAuthenticationService(val tokenService: TokenService,
                                 val userService: UserService): UserAuthenticationService {
    override fun login(username: String, password: String): Optional<String> =
        with(userService) {
            findByUsername(username)
                .filter { passwordMatches(password, it.password) }
        }.map { tokenService.expiring(mapOf("username" to username)) }

    override fun findByToken(token: String): Optional<User> =
        Optional
            .of(tokenService.verify(token))
            .map { it["username"] }
            .flatMap { userService.findByUsername(it ?: "") }

    override fun logout(user: User) {}
}
