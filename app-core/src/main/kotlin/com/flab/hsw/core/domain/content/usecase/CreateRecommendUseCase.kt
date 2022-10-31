package com.flab.hsw.core.domain.content.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.content.Recommend
import com.flab.hsw.core.domain.content.exception.ContentByIdNotFoundException
import com.flab.hsw.core.domain.content.exception.RecommendHistoryIsAlreadyExistException
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import java.util.*

interface CreateRecommendUseCase {

    fun createRecommend(message: CreateRecommendMessage): Recommend

    interface CreateRecommendMessage {
        val recommenderId: UUID
        val recommendedContentId: UUID
    }

    companion object {
        fun newInstance(
            contentRepository: ContentRepository,
            userRepository: UserRepository
        ): CreateRecommendUseCase = CreateRecommendUseCaseImpl(
            contentRepository = contentRepository,
            userRepository = userRepository
        )
    }
}

@UseCase
internal class CreateRecommendUseCaseImpl(
    private val contentRepository: ContentRepository,
    private val userRepository: UserRepository
) : CreateRecommendUseCase {
    override fun createRecommend(message: CreateRecommendUseCase.CreateRecommendMessage): Recommend {
        userRepository.findByUuid(message.recommenderId) ?: throw UserByIdNotFoundException(id = message.recommenderId)
        contentRepository.findByUuid(message.recommendedContentId) ?: throw ContentByIdNotFoundException(contentId = message.recommenderId)

        val recommendModel = Recommend.create(
            userId = message.recommenderId,
            contentId = message.recommendedContentId
        )
        contentRepository.findRecommendHistoryByUserIdAndContentId(recommendModel)?.let { throw RecommendHistoryIsAlreadyExistException() }
        return contentRepository.saveRecommendHistory(recommendModel)
    }
}
