package com.saludaunclic.semefa.gateway.service.token

interface TokenService {
    fun permanent(attributes: Map<String, String>): String
    fun expiring(attributes: Map<String, String>): String
    fun untrusted(token: String): Map<String, String>
    fun verify(token: String): Map<String, String>
}