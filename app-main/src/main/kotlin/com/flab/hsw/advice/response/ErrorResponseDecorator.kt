/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.advice.response

import com.flab.hsw.advice.errorhandler.ExceptionHandlerContract
import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.KopringException
import com.flab.hsw.core.exception.InternalException
import com.flab.hsw.core.exception.external.MalformedInputException
import com.flab.hsw.core.exception.external.WrongInputException
import com.flab.hsw.core.exception.internal.UnhandledException
import com.flab.hsw.core.i18n.LocaleProvider
import com.flab.hsw.core.i18n.MessageTemplateProvider
import com.flab.hsw.endpoint.ApiPaths
import com.flab.hsw.endpoint.ErrorResponseEnvelope
import com.flab.hsw.endpoint.ResponseEnvelope
import com.flab.hsw.exception.GeneralHttpException
import com.flab.hsw.util.originalRequestUri
import com.flab.hsw.util.toHttpStatus
import org.slf4j.Logger
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpStatusCodeException
import javax.servlet.RequestDispatcher
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest

/**
 * This class overrides the default error-handling mechanism of Spring-web.
 *
 * @since 2021-08-10
 */
@RestController
@RestControllerAdvice
class ErrorResponseDecorator(
    private val kopringExceptionHandler: ExceptionHandlerContract<KopringException>,
    private val servletExceptionHandler: ExceptionHandlerContract<ServletException>,
    private val localeProvider: LocaleProvider,
    private val messageTemplateProvider: MessageTemplateProvider,
    private val log: Logger
) : ErrorController {
    @RequestMapping(ApiPaths.ERROR)
    fun handleError(req: HttpServletRequest): ResponseEntity<ErrorResponseEnvelope> {
        (req.getAttribute(SERVLET_EXCEPTION) as? Exception)?.let { return onError(req, it) }

        val httpStatus = req.toHttpStatus()

        if (log.isErrorEnabled) {
            log.error("Unhandled servlet exception: HTTP {} : {}", httpStatus.value(), req.originalRequestUri())
        }

        return postProcessError(InternalException(ErrorCodes.UNHANDLED_EXCEPTION), httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun onError(
        req: HttpServletRequest,
        exception: Exception
    ): ResponseEntity<ErrorResponseEnvelope> {
        val (httpStatus, mtException) = dispatchExceptions(req, exception) ?: handleGenericException(exception)

        return postProcessError(httpStatus, mtException)
    }

    private fun dispatchExceptions(
        req: HttpServletRequest,
        exception: Exception
    ): Pair<KopringException, HttpStatus>? =
        when (exception) {
            is KopringException -> kopringExceptionHandler.onException(req, exception)

            is BindException -> kopringExceptionHandler.onException(
                req, WrongInputException(
                    value = exception.fieldErrors.map { it.field }.toSortedSet().joinToString { it },
                    cause = exception
                )
            )

            is HttpMessageConversionException -> kopringExceptionHandler.onException(
                req, MalformedInputException(cause = exception)
            )

            is ServletException -> servletExceptionHandler.onException(req, exception)
            is HttpStatusCodeException -> GeneralHttpException(
                exception.statusCode,
                cause = exception
            ) to exception.statusCode

            else -> null
        }

    private fun handleGenericException(exception: Exception): Pair<KopringException, HttpStatus> {
        log.error("Unhandled exception:", exception)

        return UnhandledException(cause = exception) to HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun postProcessError(
        exception: KopringException?,
        status: HttpStatus
    ): ResponseEntity<ErrorResponseEnvelope> {
        val message = if (exception == null) {
            DEFAULT_ERROR_MESSAGE
        } else {
            val clientLocale = localeProvider.locale
            val messageTemplate = messageTemplateProvider.provide(clientLocale, exception.codeBook.asMessageKey)

            exception.messageArguments()?.let {
                @Suppress("SpreadOperator")
                messageTemplate?.format(it)
            } ?: exception.message
        }

        val response = if (exception == null) {
            ResponseEnvelope.error(message, ErrorCodes.UNHANDLED_EXCEPTION.toString(), null)
        } else {
            ResponseEnvelope.error(message, exception.codeBook.code.toString(), exception.details())
        }

        return ResponseEntity(response, status)
    }

    companion object {
        private const val SERVLET_EXCEPTION = RequestDispatcher.ERROR_EXCEPTION

        private const val DEFAULT_ERROR_MESSAGE = "Cannot process this request."
    }
}
