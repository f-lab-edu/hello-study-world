package com.flab.hsw.endpoint.v1.user.login

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import com.flab.hsw.endpoint.common.response.SimpleResponse
import com.flab.hsw.endpoint.v1.user.UserLoginController
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RestController
internal class UserLoginControllerImpl(
    private val userLoginUseCase: UserLoginUseCase
)  : UserLoginController {
    override fun login(request: UserLoginRequest, session: HttpSession): SimpleResponse<Boolean> {
        session.setAttribute(User.AUTHORIZED_USER_ALIAS, userLoginUseCase.loginProcess(request))
        return SimpleResponse(true)
    }
}
