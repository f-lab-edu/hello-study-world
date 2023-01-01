package com.flab.hsw.endpoint.auth

import com.flab.hsw.endpoint.ApiPaths
import com.flab.hsw.endpoint.v1.user.login.UserLoginResponse
import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.util.JwtTokenManager.Companion.AUTHORIZATION_HEADER
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.security.auth.login.CredentialNotFoundException
import javax.servlet.http.HttpServletRequest

/**
 * ```
 * POST /auth/refresh
 *
 * Content-Type: application/json
 * ```
 */
@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface AuthenticationController {
    val jwtTokenManager: JwtTokenManager

    @RequestMapping(
        path = [ApiPaths.REFRESH_TOKEN], method = [RequestMethod.POST]
    )
    fun recreateAccessToken(request: HttpServletRequest): UserLoginResponse
}

@RestController
internal class AuthenticationControllerImpl(
    override val jwtTokenManager: JwtTokenManager
) : AuthenticationController {
    override fun recreateAccessToken(request: HttpServletRequest): UserLoginResponse {
        val claimsFromRefreshToken = jwtTokenManager.validAndReturnClaims(
            request.getHeader(AUTHORIZATION_HEADER) ?: throw CredentialNotFoundException()
        )

        return UserLoginResponse(
            authorizedToken = jwtTokenManager.createAccessTokenBy(claimsFromRefreshToken.body.subject),
            expiredIn = jwtTokenManager.returnAccessTokenExpiredIn()
        )
    }
}
