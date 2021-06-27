package com.saludaunclic.semefa.gateway.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoleDto(
    @Size(min = 4, max = 16, message = "Role debe tener al menos 4 y como máximo 16 caracteres")
    @JsonProperty("name")
    var name: String
): Serializable