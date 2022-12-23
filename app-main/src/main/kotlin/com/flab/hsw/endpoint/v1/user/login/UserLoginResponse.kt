package com.flab.hsw.endpoint.v1.user.login

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.flab.hsw.util.JwtTokenManager
import java.time.Instant

@JsonSerialize
data class UserLoginResponse(

    @JsonProperty
    val authorizedToken: String,

    @JsonProperty
    val tokenType: TokenType = TokenType.BEARER,

    @JsonProperty
    val expiredIn: Instant = JwtTokenManager.expireTime
) {
    enum class TokenType { BEARER }
}
