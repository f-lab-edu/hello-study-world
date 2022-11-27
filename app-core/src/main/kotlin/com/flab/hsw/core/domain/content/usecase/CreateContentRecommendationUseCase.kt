package com.flab.hsw.core.domain.content.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.exception.ContentByIdNotFoundException
import com.flab.hsw.core.domain.content.exception.ContentRecommendationIsAlreadyExistException
import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import java.util.*

interface CreateContentRecommendationUseCase {

    fun createContentRecommendation(message: CreateContentRecommendationMessage): ContentRecommendation

    data class CreateContentRecommendationMessage(
        val recommenderId: UUID,
        val recommendedContentId: Long
    )

    companion object {
        fun newInstance(
            contentRepository: ContentRepository,
            contentRecommendationRepository: ContentRecommendationRepository,
            userRepository: UserRepository
        ): CreateContentRecommendationUseCase = CreateContentRecommendationUseCaseImpl(
            contentRepository = contentRepository,
            contentRecommendationRepository = contentRecommendationRepository,
            userRepository = userRepository
        )
    }
}

@UseCase
internal class CreateContentRecommendationUseCaseImpl(
    private val contentRepository: ContentRepository,
    private val contentRecommendationRepository: ContentRecommendationRepository,
    private val userRepository: UserRepository
) : CreateContentRecommendationUseCase {
    override fun createContentRecommendation(
        message: CreateContentRecommendationUseCase.CreateContentRecommendationMessage
    ): ContentRecommendation {
        userRepository.findByUuid(message.recommenderId)
            ?: throw UserByIdNotFoundException(id = message.recommenderId)

        contentRepository.findById(message.recommendedContentId)
            ?: throw ContentByIdNotFoundException(contentId = message.recommendedContentId)

        val contentRecommendation = ContentRecommendation.create(
            userId = message.recommenderId,
            contentId = message.recommendedContentId
        )

        contentRecommendationRepository.findByUserIdAndContentId(contentRecommendation)
            ?.let { throw ContentRecommendationIsAlreadyExistException() }

        return contentRecommendationRepository.save(contentRecommendation)
    }
}
