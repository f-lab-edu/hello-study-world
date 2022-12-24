package com.flab.hsw.interceptor

import com.flab.hsw.core.domain.user.User.Companion.AUTHORIZED_USER_ID_ALIAS
import com.flab.hsw.endpoint.AuthenticatedUser
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class AuthenticatedUserArgumentResolver: HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticatedUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? =  (webRequest.nativeRequest as HttpServletRequest).getAttribute(AUTHORIZED_USER_ID_ALIAS) as String
}
