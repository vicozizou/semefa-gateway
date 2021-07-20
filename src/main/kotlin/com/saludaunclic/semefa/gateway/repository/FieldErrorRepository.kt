package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.FieldError
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldErrorRepository: CrudRepository<FieldError, Int>
