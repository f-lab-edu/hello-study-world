package com.flab.hsw.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class UnauthorizedException(
    override val message: String = "인증에 실패했습니다. 인증 프로세스를 다시 확인해주세요.",
    override val cause: Throwable? = null,
) :ExternalException(ErrorCodes.UNAUTHORIZED_STATUS_EXCEPTION, message, cause)
