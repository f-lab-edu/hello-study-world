/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.appconfig

import com.flab.hsw.advice.AcceptLanguageLocaleProvider
import com.flab.hsw.core.i18n.LocaleProvider
import com.flab.hsw.interceptor.AuthenticationInterceptor
import com.flab.hsw.endpoint.v1.ApiPathsV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.http.HttpHeaders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest

/**
 * @since 2021-08-10
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Autowired private lateinit var authenticationInterceptor: AuthenticationInterceptor

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun acceptLanguageLocaleProvider(
        request: HttpServletRequest
    ): LocaleProvider = AcceptLanguageLocaleProvider(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE))

    @Bean
    fun logFilter() = CommonsRequestLoggingFilter().apply {
        setIncludeQueryString(true)
        setIncludePayload(true)
        setMaxPayloadLength(LOG_PAYLOAD_LENGTH)
        setIncludeHeaders(true)
        setAfterMessagePrefix("REQUEST DATA : ")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns("${ApiPathsV1.V1}/**")
    }

    companion object {
        private const val LOG_PAYLOAD_LENGTH = 4096
    }
}
