package com.saludaunclic.semefa.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TokenConfig(@Value("\${jwt.issuer:saludaunclic}") val issuer: String,
                  @Value("\${jwt.expiration-sec:86400}") val expirationSec: Long,
                  @Value("\${jwt.clock-skew-sec:300}") val clockSkewSec: Long,
                  @Value("\${jwt.secret:secret}") val secret: String) {
    val encodedSecret: String = Base64.getEncoder().encodeToString(secret.toByteArray())
}