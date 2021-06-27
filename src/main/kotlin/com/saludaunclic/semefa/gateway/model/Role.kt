package com.saludaunclic.semefa.gateway.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("app_role")
class Role(
    @Id var id: Int? = null,
    @Column
    var name: String
): GrantedAuthority {
    override fun getAuthority(): String = name
}
