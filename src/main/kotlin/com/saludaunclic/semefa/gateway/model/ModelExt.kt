package com.saludaunclic.semefa.gateway.model

import com.saludaunclic.semefa.gateway.dto.RoleDto
import com.saludaunclic.semefa.gateway.dto.UserDto

fun toModel(role: RoleDto): Role = Role(name = role.name)

fun fromModel(role: Role): RoleDto = RoleDto(role.name)

fun toModel(user: UserDto): User =
    with(user) {
        User(
            id = id,
            username = username,
            password = password,
            encrypted = encrypted,
            status = status,
            roles = roles.map { toModel(it) }.toSet()
        )
    }

fun fromModel(user: User): UserDto =
    with(user) {
        UserDto(
            id = id,
            username = username,
            password = password,
            encrypted = encrypted,
            status = status,
            roles = roles.map { fromModel(it) }.toSet()
        )
    }
