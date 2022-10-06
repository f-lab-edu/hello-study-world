/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.advice.errorhandler

import com.flab.hsw.core.exception.KopringException
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletRequest

/**
 * @since 2021-08-10
 */
interface ExceptionHandlerContract<T : Exception> {
    fun onException(req: HttpServletRequest, exception: T): Pair<KopringException, HttpStatus>?
}
