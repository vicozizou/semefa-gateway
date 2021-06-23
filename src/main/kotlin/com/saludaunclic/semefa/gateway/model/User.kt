package com.saludaunclic.semefa.gateway.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saludaunclic.semefa.gateway.Messages
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.userdetails.UserDetails
import javax.validation.constraints.Size

@Table("app_user")
data class User(
    @Id var id: Int? = null,
    @Column
    @Size(min = 8, max = 16, message = Messages.USERNAME_SIZE_VALIDATION)
    private var username: String,
    @Column
    @Size(min = 12, max = 32, message = Messages.PASSWORD_SIZE_VALIDATION)
    private var password: String,
    @Column var encrypted: Boolean = true,
    @Column var status: UserStatus = UserStatus.DISABLED,
    @MappedCollection(idColumn = "user_id") var roles: Set<RoleRef> = setOf()
): UserDetails {
    override fun getUsername(): String = username

    fun setUsername(username: String) {
        this.username = username
    }

    @JsonIgnore override fun getPassword(): String = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities(): Set<RoleRef> = roles

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

    override fun hashCode(): Int {
        return id ?: 0
    }

    override fun toString(): String =
        "User(id=$id, username='$username', encrypted=$encrypted, status=$status, roles=$roles)"
}
