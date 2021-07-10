package com.saludaunclic.semefa.gateway.service.token

import com.saludaunclic.semefa.gateway.config.TokenConfig
import com.saludaunclic.semefa.gateway.service.date.DateService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Clock
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.impl.compression.GzipCompressionCodec
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JWTTokenService(
    private val dates: DateService,
    private val tokenConfig: TokenConfig
): Clock, TokenService {
    companion object {
        const val dot = '.'
        val compressionCodec: GzipCompressionCodec = GzipCompressionCodec()
    }

    override fun now(): Date = dates.toDate(dates.now())

    override fun permanent(attributes: Map<String, String>): String = newToken(attributes, 0)

    override fun expiring(attributes: Map<String, String>): String = newToken(attributes, tokenConfig.expirationSec)

    override fun untrusted(token: String): Map<String, String> =
        parseClaims {
            Jwts
                .parser()
                .requireIssuer(tokenConfig.issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(tokenConfig.clockSkewSec)
                .parseClaimsJws(token.substringBeforeLast(dot))
                .body
        }

    override fun verify(token: String): Map<String, String> =
        parseClaims {
            Jwts
                .parser()
                .requireIssuer(tokenConfig.issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(tokenConfig.clockSkewSec)
                .setSigningKey(tokenConfig.encodedSecret)
                .parseClaimsJws(token)
                .body
        }

    private fun newToken(attributes: Map<String, String>, expiresInSec: Long): String {
        val now = dates.now()
        val claims: Claims = Jwts
            .claims()
            .setIssuer(tokenConfig.issuer)
            .setIssuedAt(dates.toDate(now))
        if (expiresInSec > 0) {
            claims.expiration = dates.toDate(now.plusSeconds(expiresInSec))
        }
        claims.putAll(attributes)

        return Jwts
            .builder()
            .setClaims(claims)
            .signWith(HS256, tokenConfig.encodedSecret)
            .compressWith(compressionCodec)
            .compact()
    }

    private fun parseClaims(toClaims: () -> Claims): Map<String, String> =
        toClaims().entries.associate { it.key to it.value as String }
}