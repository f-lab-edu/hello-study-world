package com.flab.hsw.core.domain.content.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.command.repository.ContentCommandRepository
import java.util.*

interface CreateContentUseCase {
    fun createContent(providerUserId: UUID, message: CreateContentMessage): Content

    interface CreateContentMessage {
        val url: String
        val description: String
    }

    companion object {
        fun newInstance(
            contents: ContentCommandRepository
        ): CreateContentUseCase = CreateContentUseCaseImpl(contents = contents)
    }
}

@UseCase
internal class CreateContentUseCaseImpl(
    private val contents: ContentCommandRepository
) : CreateContentUseCase {
    override fun createContent(
        providerUserId: UUID,
        message: CreateContentUseCase.CreateContentMessage
    ): Content {
        val createContentCommand = CreateContentCommand.create(
            url = message.url,
            description = message.description,
            providerUserId = providerUserId
        )

        return contents.create(createContentCommand)
    }
}
