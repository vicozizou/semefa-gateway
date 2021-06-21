package com.saludaunclic.semefa.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ErrorsConfig(@Value("\${errors.field-error-file}") val fieldErrorFile: String,
                   @Value("\${errors.description-error-file}")val descriptionErrorFile: String) {

}
