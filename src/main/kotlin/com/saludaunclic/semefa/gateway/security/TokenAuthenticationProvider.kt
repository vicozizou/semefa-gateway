package com.saludaunclic.semefa.gateway.security

import com.saludaunclic.semefa.gateway.service.auth.UserAuthenticationService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class TokenAuthenticationProvider(val userAuthenticationService: UserAuthenticationService):
    AbstractUserDetailsAuthenticationProvider() {
    override fun additionalAuthenticationChecks(
        userDetails: UserDetails?,
        authentication: UsernamePasswordAuthenticationToken?
    ) {}

    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails =
        with(authentication?.credentials as String) {
            Optional
                .ofNullable(this)
                .flatMap { userAuthenticationService.findByToken(it) }
                .orElseThrow { UsernameNotFoundException("Cannot find user with authentication token=$this") }
        }
}

