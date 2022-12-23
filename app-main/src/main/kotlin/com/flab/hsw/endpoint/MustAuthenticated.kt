package com.flab.hsw.endpoint


/**
 * 어노테이션이 작성된 핸들러 메서드는 클라이언트의 요청을 처리할 때 반드시 인증을 필요로 합니다.
 *
 * Spring의 InterceptorRegistry에 등록된 [com.flab.hsw.interceptor.AuthenticationInterceptor]를 통해 인증 절차를 수행합니다.
 *
 * @see [com.flab.hsw.interceptor.AuthenticationInterceptor]
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MustAuthenticated
