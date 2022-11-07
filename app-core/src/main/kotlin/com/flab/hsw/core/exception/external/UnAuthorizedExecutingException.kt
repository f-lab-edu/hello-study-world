package com.flab.hsw.core.exception.external

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class UnAuthorizedExecutingException(
    override val message: String = "컨텐츠 추천은 사용자 인증이 완료된 경우에만 가능합니다.",
) :ExternalException(ErrorCodes.UNHANDLED_EXCEPTION, message)
