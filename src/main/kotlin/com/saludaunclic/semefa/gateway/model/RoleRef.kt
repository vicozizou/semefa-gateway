package com.saludaunclic.semefa.gateway.model

import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("user_role")
data class RoleRef(var roleId: String): GrantedAuthority {
    override fun getAuthority(): String = roleId
}