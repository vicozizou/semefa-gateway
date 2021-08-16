package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.DataFrame
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface DataFrameRepository: CrudRepository<DataFrame, String> {
    fun findByMessageId(messageId: String): Optional<DataFrame>
}
