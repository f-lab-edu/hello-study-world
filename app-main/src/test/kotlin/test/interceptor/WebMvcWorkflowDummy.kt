package test.interceptor

import com.flab.hsw.endpoint.MustAuthenticated
import org.springframework.web.method.HandlerMethod

/**
 * Used by only Testing
 */
private class DummyHandler {

    fun nonAuthenticatedHandlerMethod() {}

    @MustAuthenticated
    fun mustAuthenticatedHandlerMethod() {}
}

internal fun returnNonAuthenticatedHandlerMethod(): HandlerMethod =
    HandlerMethod(DummyHandler(), DummyHandler::class.java.getMethod("nonAuthenticatedHandlerMethod"))

internal fun returnMustAuthenticatedHandlerMethod(): HandlerMethod =
    HandlerMethod(DummyHandler(), DummyHandler::class.java.getMethod("mustAuthenticatedHandlerMethod"))
