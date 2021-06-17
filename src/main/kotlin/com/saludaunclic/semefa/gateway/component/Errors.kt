package com.saludaunclic.semefa.gateway.component

import com.saludaunclic.semefa.gateway.config.ErrorsConfig
import com.saludaunclic.semefa.gateway.model.Constants
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class Errors(val errorsConfig: ErrorsConfig) {
    private lateinit var errorFieldMap: Map<String, String>
    private lateinit var errorDescriptionMap: Map<String, String>

    fun getErrorField(error: String): String? {
        if (!this::errorFieldMap.isInitialized) {
            errorFieldMap = loadErrorMapFile(errorsConfig.fieldErrorFile)
        }
        return errorFieldMap[error]
    }

    fun getErrorDescription(error: String): String? {
        if (!this::errorDescriptionMap.isInitialized) {
            errorDescriptionMap = loadErrorMapFile(errorsConfig.descriptionErrorFile)
        }
        return errorDescriptionMap[error]
    }

    private fun loadErrorMapFile(fileLocation: String): Map<String, String> =
        Path.of(fileLocation)
            .toFile()
            .bufferedReader()
            .readLines()
            .associate {
                val line = it.split(Constants.SEPARATOR)
                line[0] to line[1]
            }
}
