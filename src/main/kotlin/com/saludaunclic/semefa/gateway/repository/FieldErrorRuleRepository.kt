package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.FieldErrorRule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldErrorRuleRepository: CrudRepository<FieldErrorRule, Int>
