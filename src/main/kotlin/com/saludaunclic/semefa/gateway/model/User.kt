package com.saludaunclic.semefa.gateway.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.userdetails.UserDetails

@Table("app_user")
data class User(
    @Id val id: Int? = null,
    @Column private val username: String,
    @Column private val password: String,
    @Column val encrypted: Boolean = true,
    @Column val status: UserStatus = UserStatus.DISABLED,
    @MappedCollection(keyColumn = "user_id", idColumn = "user_id") val roles: MutableSet<Role> = mutableSetOf()
): UserDetails {
    override fun getUsername(): String = username

    @JsonIgnore override fun getPassword(): String = password

    override fun getAuthorities(): Set<Role> = roles

    @JsonIgnore override fun isAccountNonExpired(): Boolean = status != UserStatus.EXPIRED

    @JsonIgnore override fun isAccountNonLocked(): Boolean = status != UserStatus.LOCKED

    @JsonIgnore override fun isCredentialsNonExpired(): Boolean = isAccountNonExpired

    @JsonIgnore override fun isEnabled(): Boolean = status == UserStatus.ENABLED

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id ?: 0

    override fun toString(): String = "User(id=$id, username='$username', status=$status, roles=$roles)"
}
