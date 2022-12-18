package com.flab.hsw.endpoint


/**
 * <p>이 어노테이션이 작성된 핸들러 메서드는 클라이언트의 요청을 처리할 때 반드시 인증을 필요로 합니다.</p>
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MustAuthenticated
