package com.saludaunclic.semefa.gateway.controller

import com.saludaunclic.semefa.gateway.service.auth.UserAuthenticationService
import com.saludaunclic.semefa.gateway.throwing.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/public/auth")
class ApiAuthController(val userAuthenticationService: UserAuthenticationService) {
    @PostMapping("/login")
    @Throws(ServiceException::class)
    fun login(
        @RequestParam("username") username: String = "",
        @RequestParam("password") password: String = ""
    ): ResponseEntity<String> = ResponseEntity.ok(
        userAuthenticationService
            .login(username, password)
            .orElseThrow { ServiceException("Usuario o password inválido", HttpStatus.UNAUTHORIZED) })
}
