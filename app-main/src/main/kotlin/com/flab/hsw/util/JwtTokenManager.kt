package com.flab.hsw.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
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
    private val accessTokenExpirePeriod: Long,
    private val refreshTokenExpirePeriod: Long
) {

    fun createBy(loginSuccessUser: User): String {
        return Jwts.builder()
            .setSubject(loginSuccessUser.loginId)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(returnAccessTokenExpiredIn()))
            .signWith(privateKey)
            .compact()
    }

    fun createRefreshToken(userLoginId: String): String {
        return Jwts.builder()
            .setIssuedAt(Date.from(Instant.now()))
            .setSubject(userLoginId)
            .setExpiration(Date.from(returnRefreshTokenExpiredIn()))
            .signWith(privateKey)
            .compact()
    }

    fun validAndReturnClaims(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token.removePrefix(BEARER_PREFIX))
    }
    fun returnAccessTokenExpiredIn(): Instant = Instant.now().plusSeconds(accessTokenExpirePeriod)

    private fun returnRefreshTokenExpiredIn(): Instant = Instant.now().plusSeconds(refreshTokenExpirePeriod)

    companion object {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
        const val REFRESH_TOKEN_COOKIE_KEY = "refreshToken"
    }
}
