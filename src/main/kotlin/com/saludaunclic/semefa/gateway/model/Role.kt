package com.saludaunclic.semefa.gateway.model

import org.springframework.security.core.GrantedAuthority

class Role(val role: String): GrantedAuthority {
    override fun getAuthority(): String = role
}