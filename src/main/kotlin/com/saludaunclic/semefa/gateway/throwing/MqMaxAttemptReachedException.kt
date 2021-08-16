package com.saludaunclic.semefa.gateway.throwing

class MqMaxAttemptReachedException(val messageId: String, message: String): Exception(message)