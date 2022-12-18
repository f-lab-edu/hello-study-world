package com.flab.hsw.endpoint.v1.content.recommend

import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase.CreateContentRecommendationMessage
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.exception.UserByLoginIDNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.endpoint.v1.content.CreateContentRecommendationController
import com.flab.hsw.endpoint.v1.content.common.ContentRecommendationResponse
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
internal class CreateContentRecommendationControllerImpl(
    override val createRecommendUseCase: CreateContentRecommendationUseCase,
    override val userRepository: UserRepository,
) : CreateContentRecommendationController {

    override fun createContentRecommendation(
        servletRequest: HttpServletRequest,
        request: CreateContentRecommendationRequest,
    ): ContentRecommendationResponse {
        val authorizedLoginId = servletRequest.getAttribute(User.AUTHORIZED_USER_ID_ALIAS) as String

        return ContentRecommendationResponse.from(
            createRecommendUseCase.createContentRecommendation(
                CreateContentRecommendationMessage(
                    recommendedContentId = request.contentId,
                    recommenderId = userRepository.findByLoginId(
                        authorizedLoginId)?.id ?: throw UserByLoginIDNotFoundException(authorizedLoginId)
                )
            )
        )
    }
}
