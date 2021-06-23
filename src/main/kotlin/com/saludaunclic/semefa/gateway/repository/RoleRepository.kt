package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: CrudRepository<Role, String>
