package com.flab.hsw.endpoint.v1.content.recommend

import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase.CreateContentRecommendationMessage
import com.flab.hsw.core.domain.user.exception.UserByLoginIDNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.endpoint.v1.content.CreateContentRecommendationController
import com.flab.hsw.endpoint.v1.content.common.ContentRecommendationResponse
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateContentRecommendationControllerImpl(
    override val createRecommendUseCase: CreateContentRecommendationUseCase,
    override val userRepository: UserRepository,
) : CreateContentRecommendationController {

    override fun createContentRecommendation(
        recommenderLoginId: String,
        request: CreateContentRecommendationRequest
    ): ContentRecommendationResponse {
        return ContentRecommendationResponse.from(
            createRecommendUseCase.createContentRecommendation(
                CreateContentRecommendationMessage(
                    recommendedContentId = request.contentId,
                    recommenderId = userRepository.findByLoginId(
                        recommenderLoginId)?.id ?: throw UserByLoginIDNotFoundException(recommenderLoginId)
                )
            )
        )
    }
}
