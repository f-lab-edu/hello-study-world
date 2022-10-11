package com.flab.hsw.core.domain.content.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.aggregate.ContentModel
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import java.util.*

interface CreateContentUseCase {
    fun createContent(providerUserId: UUID, message: CreateContentMessage): Content

    interface CreateContentMessage {
        val url: String
        val description: String
    }

    companion object {
        fun newInstance(
            contents: ContentRepository,
            users: UserRepository
        ): CreateContentUseCase = CreateContentUseCaseImpl(
            contents = contents,
            users = users
        )
    }
}

@UseCase
internal class CreateContentUseCaseImpl(
    private val contents: ContentRepository,
    private val users: UserRepository
) : CreateContentUseCase {
    override fun createContent(providerUserId: UUID, message: CreateContentUseCase.CreateContentMessage): Content {
        val provider = users.findByUuid(providerUserId) ?: throw UserByIdNotFoundException(providerUserId)

        val content = ContentModel.create(
            url = message.url,
            description = message.description,
            provider = provider
        )

        return contents.save(content)
    }
}
