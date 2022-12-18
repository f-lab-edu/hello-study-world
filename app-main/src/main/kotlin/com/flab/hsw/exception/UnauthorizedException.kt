package com.flab.hsw.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class UnauthorizedException(
    override val message: String = DEFAULT_DESC,
    override val cause: Throwable? = null,
) :ExternalException(ErrorCodes.UNAUTHORIZED_STATUS_EXCEPTION, message, cause) {
    companion object {
        const val DEFAULT_DESC = "인증에 실패했습니다. 인증 프로세스를 다시 확인해주세요."
        const val EXPIRED_TOKEN_DESC = "만료된 토큰입니다."
        const val TOKEN_NOT_FOUND_DESC = "토큰이 존재하지 않습니다."
        const val INVALID_TOKEN_DESC = "유효하지 않은 토큰입니다."
    }
}
