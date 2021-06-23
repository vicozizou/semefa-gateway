package com.saludaunclic.semefa.gateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("app_role")
data class Role(@Id var id: String): GrantedAuthority {
    override fun getAuthority(): String = id
}