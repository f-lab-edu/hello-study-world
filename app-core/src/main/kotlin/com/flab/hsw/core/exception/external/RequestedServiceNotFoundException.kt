/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.exception.external

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

/**
 * No domain service/use case is found to handle external request.
 *
 * @since 2021-08-10
 */
class RequestedServiceNotFoundException(
    override val message: String = "Requested service is not found.",
    override val cause: Throwable? = null
) : ExternalException(ErrorCodes.SERVICE_NOT_FOUND, message, cause)
