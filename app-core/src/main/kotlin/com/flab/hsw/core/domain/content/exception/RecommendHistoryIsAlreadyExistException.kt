package com.flab.hsw.core.domain.content.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ExternalException

class RecommendHistoryIsAlreadyExistException(
    override val message: String = "You can only recommend one time of each content"
) : ExternalException(ErrorCodes.CONTENT_IS_ALREADY_RECOMMENDED, message) {
    override fun messageArguments(): Collection<String> = setOf(message)
}
