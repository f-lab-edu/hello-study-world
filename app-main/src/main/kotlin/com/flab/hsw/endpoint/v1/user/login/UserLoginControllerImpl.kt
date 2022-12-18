package com.flab.hsw.endpoint.v1.user.login

import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.util.JwtTokenManager.Companion.AUTHORIZATION_HEADER
import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import com.flab.hsw.endpoint.common.response.SimpleResponse
import com.flab.hsw.endpoint.v1.user.UserLoginController
import org.springframework.context.annotation.PropertySource
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
internal class UserLoginControllerImpl(
    private val userLoginUseCase: UserLoginUseCase,
    private val jwtTokenManager: JwtTokenManager
)  : UserLoginController {
    override fun login(request: UserLoginRequest, response: HttpServletResponse): SimpleResponse<Boolean> {
        val loginSuccessUser = userLoginUseCase.loginProcess(request)
        response.addHeader(AUTHORIZATION_HEADER, jwtTokenManager.createBy(loginSuccessUser))
        return SimpleResponse(true)
    }
}
