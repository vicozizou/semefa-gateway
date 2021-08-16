package com.saludaunclic.semefa.gateway.repository

import com.saludaunclic.semefa.gateway.model.DataFrame
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DataFrameRepository: CrudRepository<DataFrame, String>
