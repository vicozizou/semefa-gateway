package com.saludaunclic.semefa.gateway.component

import com.saludaunclic.semefa.gateway.repository.FieldErrorRepository
import com.saludaunclic.semefa.gateway.repository.FieldErrorRuleRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Errors(
    val fieldErrorRepository: FieldErrorRepository,
    val fieldErrorRuleRepository: FieldErrorRuleRepository
) {
    companion object {
        val lock = Object()
    }

    private lateinit var fieldErrorMap: Map<Int, String>
    private lateinit var fieldErrorRuleMap: Map<Int, String>

    fun getFieldError(id: Int): String? {
        return fieldErrorMap[id]
    }

    fun getFieldErrorRule(id: Int): String? {
        return fieldErrorRuleMap[id]
    }

    @PostConstruct
    fun reset() {
        synchronized(lock) {
            fieldErrorMap = fieldErrorRepository
                .findAll()
                .associate { it.id to it.name }
            fieldErrorRuleMap = fieldErrorRuleRepository
                .findAll()
                .associate { it.id to it.description }
        }
    }
}
