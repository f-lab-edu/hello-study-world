package com.flab.hsw.core.domain.content.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.external.MalformedInputException

class ContentProviderNotFoundException(
    private val contentId: Long,
    override val message: String = "Content('$contentId') provider is not found.",
) : MalformedInputException(ErrorCodes.CONTENT_PROVIDER_NOT_FOUND, message) {
    override fun messageArguments(): Collection<String> = setOf(contentId.toString())
}
