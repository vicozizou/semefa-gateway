package com.saludaunclic.semefa.gateway.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.saludaunclic.semefa.gateway.model.UserStatus
import java.io.Serializable
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @JsonProperty("id")
    var id: Int? = null,
    @Size(min = 8, max = 16, message = "Usuario debe tener al menos 8 y como máximo 16 caracteres")
    @JsonProperty("username")
    var username: String,
    @Size(min = 12, max = 32, message = "Contraseña debe tener al menos 12 y como máximo 32 caracteres")
    @JsonProperty("password")
    var password: String,
    @JsonProperty("encrypted") var encrypted: Boolean = true,
    @JsonProperty("status") var status: UserStatus = UserStatus.DISABLED,
    @JsonProperty("roles") var roles: Set<RoleDto> = setOf()
): Serializable {
    override fun toString(): String {
        return "UserDto(id=$id, username='$username', status=$status, roles=$roles)"
    }
}