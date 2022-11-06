package com.flab.hsw.core.domain.content.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class ContentRecommendationIsAlreadyExistException(
    override val message: String = "각 컨텐츠에 대해서는 한번의 추천만 가능합니다."
) : ExternalException(ErrorCodes.CONTENT_ONLY_RECOMMENDED_ONCE, message) {
    override fun messageArguments(): Collection<String> = setOf(message)
}
