package com.flab.hsw.core.domain.user.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class InvalidUserPasswordException(
    override val message: String = "입력하신 패스워드가 일치하지 않습니다.",
    override val cause: Throwable? = null
): ExternalException(ErrorCodes.USER_PASSWORD_IS_INVALID, message)
