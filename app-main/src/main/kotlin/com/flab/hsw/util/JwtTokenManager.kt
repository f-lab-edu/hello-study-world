package com.flab.hsw.util

import com.flab.hsw.core.domain.user.User
import io.jsonwebtoken.*
import java.security.PrivateKey
import java.security.PublicKey
import java.time.Instant
import java.util.*

/**
 * 인가를 위한 JWT(JSON WEB TOKEN)의 발급 및 검증을 담당하는 객체입니다.
 *
 * Spring Bean으로 등록해 사용합니다.
 *
 * @see [com.flab.hsw.appconfig.bean.AuthenticationBeans.jwtTokenManager]
 */
class JwtTokenManager(
    private val publicKey: PublicKey,
    private val privateKey: PrivateKey,
    private val expirePeriod: Long
) {

    fun createBy(loginSuccessUser: User): String {
        return Jwts.builder()
            .setSubject(loginSuccessUser.loginId)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(returnExpiredIn()))
            .signWith(privateKey)
            .compact()
    }

    fun validAndReturnClaims(token: String): Jws<Claims>? {
        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token.removePrefix(BEARER_PREFIX))
    }

    fun returnExpiredIn(): Instant = Instant.now().plusSeconds(expirePeriod)

    companion object {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "

        private val expireTime: Instant = Instant.now().plusSeconds(7200L)
    }
}
