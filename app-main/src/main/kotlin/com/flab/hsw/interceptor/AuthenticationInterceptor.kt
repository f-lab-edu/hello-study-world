package com.flab.hsw.interceptor

import com.flab.hsw.core.domain.user.User.Companion.AUTHORIZED_USER_ID_ALIAS
import com.flab.hsw.endpoint.MustAuthenticated
import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.util.JwtTokenManager.Companion.AUTHORIZATION_HEADER
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.security.auth.login.CredentialNotFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    private val jwtTokenManager: JwtTokenManager
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (shouldAuthenticated(handler)) {
            val claims = jwtTokenManager.validAndReturnClaims(
                request.getHeader(AUTHORIZATION_HEADER) ?: throw CredentialNotFoundException()
            )
            request.setAttribute(AUTHORIZED_USER_ID_ALIAS, claims.body.subject)
        }
        return true
    }

    private fun shouldAuthenticated(handler: Any) = handler is HandlerMethod
            && handler.hasMethodAnnotation(MustAuthenticated::class.java)
}
