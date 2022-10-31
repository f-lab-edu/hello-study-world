package com.flab.hsw.core.domain.content.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.external.MalformedInputException
import java.util.UUID

class ContentByIdNotFoundException(
    val contentId: UUID,
    override val message: String = "Content('$contentId') is not found.",
) : MalformedInputException(ErrorCodes.CONTENT_BY_ID_NOT_FOUND, message){
    override fun messageArguments(): Collection<String> = setOf(contentId.toString())
}
