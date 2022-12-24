package com.flab.hsw.endpoint

/**
 * [com.flab.hsw.interceptor.AuthenticationInterceptor]를 통해 인증 상태가 검증된된 사용자의 세부 정보를 매핑하기 위해 사용됩니다.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticatedUser()
