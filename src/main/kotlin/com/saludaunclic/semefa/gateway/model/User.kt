package com.saludaunclic.semefa.gateway.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Size

class User(@Id val id: String,
           @Size(min = 8, max = 16, message = "Usuario debe tener al menos 8 y como máximo 16 caracteres")
           private val username: String,
           @Size(min = 12, max = 32, message = "Contraseña debe tener al menos 12 y como máximo 32 caracteres")
           private val password: String,
           val encrypted: Boolean = true,
           val status: UserStatus = UserStatus.DISABLED,
           val roles: List<Role> = listOf()): UserDetails {
    override fun getUsername(): String = username

    @JsonIgnore override fun getPassword(): String = password

    @JsonIgnore override fun getAuthorities(): List<Role> = roles

    @JsonIgnore override fun isAccountNonExpired(): Boolean = status != UserStatus.EXPIRED

    @JsonIgnore override fun isAccountNonLocked(): Boolean = status != UserStatus.LOCKED

    @JsonIgnore override fun isCredentialsNonExpired(): Boolean = isAccountNonExpired

    override fun isEnabled(): Boolean = status == UserStatus.ENABLED
}