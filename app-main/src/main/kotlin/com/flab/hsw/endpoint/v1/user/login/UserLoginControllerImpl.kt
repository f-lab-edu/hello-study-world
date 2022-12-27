package com.flab.hsw.endpoint.v1.user.login

import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import com.flab.hsw.endpoint.v1.user.UserLoginController
import org.springframework.web.bind.annotation.RestController

@RestController
internal class UserLoginControllerImpl(
    private val userLoginUseCase: UserLoginUseCase,
    private val jwtTokenManager: JwtTokenManager
) : UserLoginController {
    override fun login(request: UserLoginRequest): UserLoginResponse {
        return UserLoginResponse(
            authorizedToken = jwtTokenManager.createBy(userLoginUseCase.loginProcess(request)),
            expiredIn = jwtTokenManager.returnExpiredIn()
        )
    }
}
