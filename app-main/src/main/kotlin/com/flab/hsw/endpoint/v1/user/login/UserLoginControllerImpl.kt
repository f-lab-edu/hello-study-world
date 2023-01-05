package com.flab.hsw.endpoint.v1.user.login

import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import com.flab.hsw.endpoint.v1.user.UserLoginController
import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.util.JwtTokenManager.Companion.REFRESH_TOKEN_COOKIE_KEY
import com.flab.hsw.util.addCookieWithHttpOnly
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
internal class UserLoginControllerImpl(
    private val userLoginUseCase: UserLoginUseCase,
    private val jwtTokenManager: JwtTokenManager
) : UserLoginController {
    override fun login(request: UserLoginRequest, response: HttpServletResponse): UserLoginResponse {
        val loginSuccessUser = userLoginUseCase.loginProcess(request)

        response.addCookieWithHttpOnly(Pair(
            REFRESH_TOKEN_COOKIE_KEY,
            jwtTokenManager.createRefreshToken(loginSuccessUser.loginId)))

        return UserLoginResponse(
            authorizedToken = jwtTokenManager.createAccessTokenBy(loginSuccessUser.loginId),
            expiredIn = jwtTokenManager.returnAccessTokenExpiredIn()
        )
    }
}
