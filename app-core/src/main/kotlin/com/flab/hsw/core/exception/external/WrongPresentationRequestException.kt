/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.exception.external

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

/**
 * @since 2021-08-10
 */
class WrongPresentationRequestException(
    override val message: String = "Requested data presentation is not supported.",
    override val cause: Throwable? = null
) : ExternalException(ErrorCodes.WRONG_PRESENTATION, message, cause)
