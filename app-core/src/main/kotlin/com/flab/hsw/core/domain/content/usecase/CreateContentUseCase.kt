package com.flab.hsw.core.domain.content.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.CreateContent
import com.flab.hsw.core.domain.content.repository.ContentRepository
import java.util.*

interface CreateContentUseCase {
    fun createContent(providerUserId: UUID, message: CreateContentMessage): Content

    interface CreateContentMessage {
        val url: String
        val description: String
    }

    companion object {
        fun newInstance(
            contents: ContentRepository
        ): CreateContentUseCase = CreateContentUseCaseImpl(contents = contents)
    }
}

@UseCase
internal class CreateContentUseCaseImpl(
    private val contents: ContentRepository
) : CreateContentUseCase {
    override fun createContent(
        providerUserId: UUID,
        message: CreateContentUseCase.CreateContentMessage
    ): Content {
        val createContent = CreateContent.create(
            url = message.url,
            description = message.description,
            providerUserId = providerUserId
        )

        return contents.create(createContent)
    }
}
