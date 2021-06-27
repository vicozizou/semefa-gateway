package com.saludaunclic.semefa.gateway.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AppSetupDto(
    @NotNull(message = "Usuario debe incluirse")
    @JsonProperty("user")
    var user: UserDto
): Serializable {
    override fun toString(): String {
        return "AppSetupDto(user=$user)"
    }
}
